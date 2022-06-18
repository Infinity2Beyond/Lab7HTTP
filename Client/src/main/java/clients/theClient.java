package clients;

import clients.utility.authorizedHandle;
import clients.utility.userHandle;
import common.data.humanbeing;
import common.exceptions.ConnectionError;
import common.exceptions.NotInDeclaredLimit;
import common.interaction.User;
import common.interaction.request;
import common.interaction.response;
import common.interaction.responseCode;
import common.utility.outPuter;
import common.HTTP.*;

import java.io.*;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Stack;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;




public class theClient {
	private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private userHandle userhandle;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;
    private final authorizedHandle authHandler;
    private User user;
    private Gson gson = new Gson() ;
    private HttpRequest requestHTTPToServer;
    
    private String HTTP_CONNECT ;
    private final String COMMUNICATE_FILE ="C:\\Users\\Admin\\OneDrive\\Desktop\\http.json";


    public theClient(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, userHandle userhandle, authorizedHandle authHandler ) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userhandle = userhandle;
        this.authHandler = authHandler;

    }
    /**
     * Write request to json file
     * @param object
     */
    public void writeRequest(HttpRequest string) {
	       if (string != null) {
	           try (OutputStreamWriter collectionFileWriter = 
	        		new OutputStreamWriter(new FileOutputStream(new File(COMMUNICATE_FILE)))) {
	               collectionFileWriter.write(gson.toJson(string));               
	           } catch (IOException exception) {
	        	   outPuter.printerror("The download file is a directory/cannot be opened!");
	           }
	        }  
    }
    
    /**
     * Read response from json file
     * @return 
     *
    public HttpResponse readResponse() throws IOException {
 		 try (InputStreamReader collectionFileReader 
 		     = new InputStreamReader(new FileInputStream (COMMUNICATE_FILE))) {   		
 			 	HttpResponse HttpResponse = gson.fromJson(collectionFileReader, HttpResponse.class);
 			 	
          	    System.out.println(HttpResponse.toString());
              //System.out.println(collectionFileReader);
                return HttpResponse;
 		 } catch (FileNotFoundException exception) {
 			    response ResponseRaw = new response (responseCode.OK, null);
 			 	bodyResponseHttp body = new bodyResponseHttp(ResponseRaw);
 			     return new HttpResponse(null,null,body);
          }  catch (JsonParseException | NullPointerException exception) {
        	  response ResponseRaw = new response (responseCode.OK, null);
			 	bodyResponseHttp body = new bodyResponseHttp(ResponseRaw);
			     return new HttpResponse(null,null,body);
          
          } catch (IllegalStateException exception) {
         	 outPuter.printerror("Unexpected error!");
          }
 		 	response ResponseRaw = new response (responseCode.OK, null);
		 	bodyResponseHttp body = new bodyResponseHttp(ResponseRaw);
		     return new HttpResponse(null,null,body);
 	 } 
   
    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionError, NotInDeclaredLimit {
    	try {
            if (reconnectionAttempts >= 1) outPuter.println("Reconnecting to the server...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));      
            outPuter.println("The connection to the server has been established.");
            outPuter.println("Waiting for permission to share data...");
            
            HTTP_CONNECT  = "GET / HTTP/1.1" + "\r\n" 
    				+ "Host: " + host + ":" + port + "\r\n"
    				+ "Connection: keep-alive" ;
            
            //writeRequest(HTTP_CONNECT);
            serverWriter = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            serverReader = new ObjectInputStream(socketChannel.socket().getInputStream());
            outPuter.println("Permission to share data received.");
                     
        } catch (IllegalArgumentException exception) {
        	outPuter.printerror("Server address entered incorrectly!");
            throw new NotInDeclaredLimit();
        } catch (IOException exception) {
        	outPuter.printerror("An error occurred while connecting to the server!");
            throw new ConnectionError();
        }
    }  
    
    /**
     * Server request process.
     * @throws IOException 
     */
    private boolean processRequestToServer() {
        request requestToServer = null;
        response serverResponse = null;       
        do {
            try {    
                requestToServer = serverResponse != null ? userhandle.handle(serverResponse.getResponseCode(), user) :
                        userhandle.handle(null, user);
                if (requestToServer.isEmpty()) continue;
                                              
                firstLineHttp firstLine = new firstLineHttp(requestToServer.getCode(), requestToServer);
                bodyHttp body = new bodyHttp(requestToServer);
                headerHttp header = new headerHttp(requestToServer.getUser(), host, "text/plain", body.toString().length(), "keep alive" );
                HttpRequest request = new HttpRequest(firstLine, header, body);
                
                serverWriter.writeObject(gson.toJson(request));
                
                
                
                HttpResponse reponseHttp = new HttpResponse();
                String gsonString = (String) serverReader.readObject();
                reponseHttp = gson.fromJson(gsonString, reponseHttp.getClass());
                
                serverResponse = reponseHttp.getBody().getResponseFromServer();
                outPuter.println(serverResponse.getResponseCode().STATUSCODE +" "+ serverResponse.getResponseCode().MESSAGE);
                outPuter.print(serverResponse.getResponseBody());
                
                
            } catch (InvalidClassException | NotSerializableException exception) {
            	outPuter.printerror(exception);
            	outPuter.printerror("An error occurred while sending data to the server!");
          } catch (ClassNotFoundException exception) {
            	outPuter.printerror("An error occurred while reading received data!");
            } catch (IOException exception) {
            	outPuter.printerror(" Server connection lost!");
                try {
                    reconnectionAttempts++;
                    connectToServer();
                } catch (ConnectionError | NotInDeclaredLimit reconnectionException) {
                    if (requestToServer.getCommandName().equals("EXIT"))
                    	outPuter.println("The command will not be registered on the server.");
                    else outPuter.println("Try repeating the command later.");
                }
            } catch (NullPointerException e) {
            	outPuter.printerror("Catched NullPointerExeption"); 
            }
        } while (!requestToServer.getCommandName().equals("EXIT"));
        return false;
    }
    /**
     * Clear file communicate
     */
    public void ClearFile () {
    	PrintWriter writer;
		try {
			writer = new PrintWriter(new File(COMMUNICATE_FILE));
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
    }
    /**
     * Handle process authentication.
     */
    private void processAuthentication() {
        request requestToServer = null;
        response serverResponse = null;
        do {
            try {
                requestToServer = authHandler.handle();
                if (requestToServer.isEmpty()) continue;
                //serverWriter.writeObject(requestToServer);
                firstLineHttp firstLine = new firstLineHttp(requestToServer.getCode(), requestToServer);
                bodyHttp body = new bodyHttp(requestToServer);
                headerHttp header = new headerHttp(requestToServer.getUser(), host, "text/plain", body.toString().length(), "keep alive" );
                HttpRequest request = new HttpRequest(firstLine, header, body);
                
                serverWriter.writeObject(gson.toJson(request));
                
                HttpResponse reponseHttp = new HttpResponse();
                String gsonString = (String) serverReader.readObject();
                reponseHttp = gson.fromJson(gsonString, reponseHttp.getClass());
                
                serverResponse = reponseHttp.getBody().getResponseFromServer();
                outPuter.println(serverResponse.getResponseCode().STATUSCODE +" "+ serverResponse.getResponseCode().MESSAGE);
                outPuter.print(serverResponse.getResponseBody());

                //System.out.println(serverResponse);               
                //ClearFile();
                if (serverResponse.getResponseCode().equals(responseCode.OK_200_OK)
        		|| serverResponse.getResponseCode().equals(responseCode.ACCEPTED_201_ACCEPTED)) {
                	break;
                }
          } catch (InvalidClassException | NotSerializableException exception) {
            	outPuter.printerror("An error occurred while sending data to the server!");
            } catch (ClassNotFoundException exception) {
            	outPuter.printerror("An error occurred while reading the received data!");
           } catch (IOException exception) {
            	outPuter.printerror("The connection to the server has been broken!");
                try {
                    connectToServer();
                } catch (ConnectionError | NotInDeclaredLimit reconnectionException) {
                	outPuter.println("Please try again later.");
                }
            } catch (JsonSyntaxException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (JsonIOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            
        } while (serverResponse == null || !serverResponse.getResponseCode().equals(responseCode.OK_200_OK)
        		|| !serverResponse.getResponseCode().equals(responseCode.ACCEPTED_201_ACCEPTED));
        user = requestToServer.getUser();
    }

    /**
     * Begins client operation.
     */
    public void run() {
        try {
            while (true) {
                try {
                    connectToServer();
                    processAuthentication();
                    processRequestToServer();
                    break;
                } catch (ConnectionError exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                    	outPuter.printerror("Connection attempts exceeded!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                    	outPuter.printerror("Connection timeout '" + reconnectionTimeout + "' is out of range!");
                    	outPuter.println("Reconnection will be made immediately.");
                    } catch (Exception timeoutException) {
                    	outPuter.printerror("An error occurred while trying to connect!");
                    	outPuter.println("Reconnection will be made immediately.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            outPuter.println("Client job completed.");
        } catch (NotInDeclaredLimit exception) {
        	outPuter.printerror("The client cannot be started!");
        } catch (IOException exception) {
        	outPuter.printerror("An error occurred while trying to terminate the connection to the server!");
        }
    }
}
