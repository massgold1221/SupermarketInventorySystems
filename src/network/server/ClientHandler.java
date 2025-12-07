package network.server;

import network.protocol.*;
import java.io.*;
import java.net.Socket;

/**
 * ClientHandler: Handles individual client connections
 * Extracted from main server for better separation of concerns
 */
public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private SessionManager sessionManager;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private String sessionId;
    
    public ClientHandler(Socket clientSocket, SessionManager sessionManager) {
        this.clientSocket = clientSocket;
        this.sessionManager = sessionManager;
    }
    
    @Override
    public void run() {
        try {
            // Initialize streams
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            
            // Read commands and process them
            while (clientSocket.isConnected()) {
                try {
                    Object obj = inputStream.readObject();
                    if (obj instanceof RemoteCommand) {
                        RemoteCommand command = (RemoteCommand) obj;
                        RemoteResponse response = handleCommand(command);
                        outputStream.writeObject(response);
                        outputStream.flush();
                    }
                } catch (EOFException e) {
                    System.out.println("[HANDLER] Client disconnected");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[HANDLER] Error handling client: " + e.getMessage());
        } finally {
            closeConnection();
        }
    }
    
    /**
     * Process incoming command
     */
    private RemoteResponse handleCommand(RemoteCommand command) {
        CommandProcessor processor = new CommandProcessor(sessionManager);
        return processor.processCommand(command);
    }
    
    /**
     * Close the connection
     */
    private void closeConnection() {
        try {
            if (sessionId != null) {
                sessionManager.endSession(sessionId);
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
        } catch (IOException e) {
            System.err.println("[HANDLER] Error closing connection: " + e.getMessage());
        }
    }
}
