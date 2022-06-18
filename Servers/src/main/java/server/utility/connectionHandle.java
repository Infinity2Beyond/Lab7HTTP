package server.utility;

import common.HTTP.HttpRequest;
import common.HTTP.HttpResponse;
import common.HTTP.bodyResponseHttp;
import common.HTTP.firstLineResponseHttp;
import common.HTTP.headerResponseHttp;
import common.interaction.humanRaw;
import common.interaction.request;
import common.interaction.response;
import common.interaction.responseCode;
import common.utility.outPuter;
import server.app;
import server.theServer;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

public class connectionHandle implements Runnable {
	private theServer server;
    private Socket clientSocket;
    private CommandManager commandManager;
    private ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
    private final ExecutorService cachedThreadPool =   Executors.newCachedThreadPool();
    private List<requestHandle> tasks = new ArrayList<>();
    private Gson gson = new Gson() ;
    
    public connectionHandle(theServer server, Socket clientSocket, CommandManager commandManager) {
        this.server = server;
        this.clientSocket = clientSocket;
        this.commandManager = commandManager;
    }
    
    /**
     * Main handling cycle. The server receives a request from the client, processes it, and sends a response to the client
     */
    @Override
    public void run() {
        request userRequest = null;
        response responseToUser = null;
        boolean stopFlag = false;
        try (ObjectInputStream clientReader = new ObjectInputStream(clientSocket.getInputStream());
        		ObjectOutputStream clientWriter = new ObjectOutputStream(clientSocket.getOutputStream())) {
            do {
                /*
                TODO: Tạo một class Runnale (hoặc Callable) mới để đọc Object
                 */
                            	
            	String gsonString = (String) clientReader.readObject();
            	HttpRequest HttpRequest = new HttpRequest();
                HttpRequest = gson.fromJson(gsonString, HttpRequest.getClass());
                app.logger.info("RequestHTTP '" + "\r\n"+ HttpRequest.toString());
                                
                userRequest = HttpRequest.GetFirstLine().getRequest();
                app.logger.info("Request '" + userRequest.getCommandName() + "' processed.");

                
                
                requestHandle handleRequest = new requestHandle(userRequest, commandManager);               
                tasks.add(handleRequest);
                responseToUser = cachedThreadPool.invokeAny(tasks);
                tasks.clear();                

                
                response finalResponseToUser = responseToUser;
                      
                
                firstLineResponseHttp firstLine = new firstLineResponseHttp(finalResponseToUser.getResponseCode());
            	headerResponseHttp header = new headerResponseHttp("Local Host", LocalDateTime.now(),"Text/plain", 
            			finalResponseToUser.getResponseBody().length(), "Keep Alive");
            	bodyResponseHttp body = new bodyResponseHttp(finalResponseToUser);
            	
            	HttpResponse HttpResponse = new HttpResponse(firstLine, header, body);
            	
            	app.logger.info("Http Response: " + "\r\n" + HttpResponse.toString());

                
            	if (!forkJoinPool.submit(() -> {
                  	try {
                  	       
                    clientWriter.writeObject(gson.toJson(HttpResponse, HttpResponse.getClass()));
                    clientWriter.flush();
                    //System.out.println("heelo");
                	//server.writeResponse (FinalResponse);              	
                    return true ;  
                  	} catch (IOException exception) {
                   	outPuter.printerror("Unexpected termination of connection with the client!");
                    	app.logger.warn("Unexpected termination of connection with the client!");
                  	}
                return false; 
            }).get()) break; 
                
            } while (responseToUser.getResponseCode() != responseCode.SERVER_EXIT
                    );
            if (responseToUser.getResponseCode() == responseCode.SERVER_EXIT)
                stopFlag = true;
        } catch (ClassNotFoundException exception) {
        	outPuter.printerror("An error occurred while reading the received data!");
        	app.logger.error("An error occurred while reading the received data!");
        } catch (CancellationException | ExecutionException | InterruptedException exception) {
        	outPuter.println(exception + "A multithreading error occurred while processing the request!");
        	app.logger.warn(exception + "A multithreading error occurred while processing the request!");
        } catch (IOException exception) {
        	outPuter.printerror("Unexpected termination of connection with the client!");
        	app.logger.warn("Unexpected termination of connection with the client!");
        } finally {
            try {
            	forkJoinPool.shutdown();
                clientSocket.close();
                outPuter.println("The client is disconnected from the server.");
                app.logger.info("The client is disconnected from the server.");
            } catch (IOException exception) {
                outPuter.printerror("An error occurred while trying to terminate the connection with the client!");
                app.logger.error("An error occurred while trying to terminate the connection with the client!");
            }
            if (stopFlag) server.stop();
            server.releaseConnection();
        }
    }
}
