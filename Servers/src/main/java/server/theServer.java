package server;

import common.HTTP.HttpRequest;
import common.HTTP.HttpResponse;
import common.HTTP.firstLineHttp;
import common.exceptions.ClosingSocket;
import common.exceptions.ConnectionError;
import common.exceptions.OpeningServerSocket;
import common.interaction.request;
import common.utility.outPuter;
import server.utility.CommandManager;
import server.utility.connectionHandle;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


public class theServer {
	private int port ;
    
    private ServerSocket serverSocket;
    
    private final CommandManager commandManager;
    private boolean Stopped;
    private request userRequest = null;
    private final Semaphore semaphore;
    private ForkJoinPool forkJoinPool = new ForkJoinPool() ;
    private Gson gson = new Gson() ;

    
    private String  HTTP_CONNECT  ;
    public final String COMMUNICATE_FILE ="C:\\Users\\Admin\\OneDrive\\Desktop\\http.json";


    
    public theServer(int port, int maxClients, CommandManager commandManager) {
        this.port = port;
        //this.soTimeout = soTimeout;
        this.commandManager = commandManager;
        this.semaphore = new Semaphore(maxClients);
    }   
    /**
     * Open server socket.
     */
    private void openServerSocket() throws OpeningServerSocket {
        try {
            app.logger.info("Server start...");
            serverSocket = new ServerSocket(port);
            app.logger.info("The server is running.");
            outPuter.println("Port listening '" + port + "'...");
            app.logger.info("Port listening '" + port + "'...");
        } catch (IllegalArgumentException exception) {
            outPuter.printerror("Port '" + port + "' is out of range!");
            app.logger.error("Port '" + port + "' is out of range!");
            throw new OpeningServerSocket();
        } catch (IOException exception) {
            outPuter.printerror("An error occurred while trying to use the port '" + port + "'!");
            app.logger.error("An error occurred while trying to use the port '" + port + "'!");
            throw new OpeningServerSocket();
        }
    }
    /**
     * Write request to json file
     * @param object
     */
    public void writeResponse(HttpResponse string) throws IOException {
	       if (string != null) {
	           try (OutputStreamWriter collectionFileWriter = 
	        		new OutputStreamWriter(new FileOutputStream(new File(COMMUNICATE_FILE)))) {
	               collectionFileWriter.write(gson.toJson(string));               
	           } 
	        }  
    }
    
    /**
     * Read response from json file
     * @return 
     *
    public HttpRequest readRequest() {
  		 try (InputStreamReader collectionFileReader 
  		     = new InputStreamReader(new FileInputStream (COMMUNICATE_FILE))) {   		
  			 	HttpRequest HttpRequest ;            
               HttpRequest = gson.fromJson(collectionFileReader, HttpRequest.class);
           	   

               //System.out.println(collectionFileReader);
               if (HttpRequest == null) throw new NoSuchElementException() ;                 
               return HttpRequest;
  		 } catch (FileNotFoundException exception) {
  			 	firstLineHttp first = new firstLineHttp(HttpMethod.GET, null);
  			     return new HttpRequest(first,null,null);
           } catch (NoSuchElementException exception) {
        	   firstLineHttp first = new firstLineHttp(HttpMethod.GET, null);
			     return new HttpRequest(first,null,null);
           } catch (JsonParseException | NullPointerException exception) {
        	   firstLineHttp first = new firstLineHttp(HttpMethod.GET, null);
			     return new HttpRequest(first,null,null);
           } catch (IOException exception) {
          	 outPuter.printerror("The download file is a directory/cannot be opened!");
           } catch (IllegalStateException exception) {
          	 outPuter.printerror("Unexpected error!");
           }
  		 	firstLineHttp first = new firstLineHttp(HttpMethod.GET, null);
		     return new HttpRequest(first,null,null);
  	 } 
    
    /**
     * Connecting to client.
     */
    private Socket connectToClient() throws ConnectionError {
        try {                       
            Socket clientSocket = serverSocket.accept();
            outPuter.println("The connection with the client has been established.");
            app.logger.info("The connection with the client has been established.");
            return clientSocket;
        } catch (IOException exception) {
            throw new ConnectionError();
        }
    }
    /**
     * Checked stops of server.
     *
     * @return Status of server stop.
     */
    private synchronized boolean Stopped() {
        return Stopped;
    }
    /**
     * Finishes server operation.
     */
    public void stop() {
        try {
        	app.logger.info("Shutting down the server...");
            if (serverSocket == null) throw new ClosingSocket();
            Stopped = true; 
            forkJoinPool.shutdown();
            serverSocket.close();
            outPuter.println("Server completed successfully.");
            app.logger.info("Server completed successfully.");
        } catch (ClosingSocket exception) {
        	outPuter.printerror("Unable to shut down server not yet running!");
            app.logger.error("Unable to shut down server not yet running!");
        } catch (IOException exception) {
        	outPuter.printerror("An error occurred while shutting down the server!");
            app.logger.error("An error occurred while shutting down the server!");
        }
    }
    /**
     * It tries to acquire a permit from the semaphore, if it fails, it prints an error message
     */
    public void acquireConnection() {
        try {
            semaphore.acquire();
            app.logger.info("Permission for a new connection has been received.");
        } catch (InterruptedException exception) {
            outPuter.printerror("An error occurred while obtaining permission for a new connection!");
            app.logger.error("An error occurred while obtaining permission for a new connection!");
        }
    }

    /**
     * Release a connection from the pool.
     */
    public void releaseConnection() {
        semaphore.release();
        app.logger.info("Connection disconnect registered.");
    }
    
    /**
     * Clear file communicate
     */
    public void ClearFile (String file) {
    	PrintWriter writer;
		try {
			writer = new PrintWriter(new File(file));
			writer.print("");
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		}
    }
    /**
     * The server opens a socket, waits for a client to connect, and then creates a new thread to handle the connection
     */
    public void run() {
        try {
            openServerSocket();
            
            while (!Stopped()) {
            	//readResponse();
            	//HTTP_CONNECT = "GET / HTTP/1.1" + "\r\n" 
        		//		+ "Host: " + "127.0.0.1" + ":" + port + "\r\n"
        		//		+ "Connection: keep-alive" ;
            	//if (readResponse().equals(HTTP_CONNECT)) {
            	//	ClearFile();          		
                try {
                    acquireConnection();
                    if (Stopped()) throw new ConnectionError();
                    Socket clientSocket = connectToClient(); 
                    connectionHandle task = new connectionHandle(this, clientSocket, commandManager);
                    
                    forkJoinPool.execute(task);
                    //Thread readingRequest = new Thread(task);
                    app.logger.info("Received a new request.");
                    //readingRequest.start();
                    
                } catch (ConnectionError exception) {
                    if (!Stopped()) {
                        outPuter.printerror("An error occurred while connecting to the client!");
                        app.logger.error("An error occurred while connecting to the client!");
                    } else break;
                }
            }
//            }
            
            forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            outPuter.println("The server has ended.");
        } catch (OpeningServerSocket exception) {
            outPuter.printerror("The server cannot be started!");
            app.logger.error("The server cannot be started!");
       } catch (InterruptedException e) {
            outPuter.printerror("An error occurred while shutting down already connected clients!");        
    }       
    }    
    
}
