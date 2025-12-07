package network.client;

import network.protocol.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.*;

/**
 * InventoryClient: Client for remote inventory access
 * Enhanced with reconnect logic
 */
public class InventoryClient {
    private String serverHost;
    private int serverPort;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private String sessionId;
    private boolean connected;
    private static final int RECONNECT_ATTEMPTS = 3;
    private static final long RECONNECT_DELAY_MS = 2000;
    
    public InventoryClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.connected = false;
    }
    
    /**
     * Connect to the server
     */
    public boolean connect() {
        int attempts = 0;
        while (attempts < RECONNECT_ATTEMPTS) {
            try {
                socket = new Socket(serverHost, serverPort);
                outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.flush();
                inputStream = new ObjectInputStream(socket.getInputStream());
                
                connected = true;
                System.out.println("[CLIENT] Connected to server " + serverHost + ":" + serverPort);
                return true;
            } catch (IOException e) {
                attempts++;
                System.err.println("[CLIENT] Connection attempt " + attempts + " failed: " + e.getMessage());
                
                if (attempts < RECONNECT_ATTEMPTS) {
                    try {
                        Thread.sleep(RECONNECT_DELAY_MS);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * Send command and receive response
     */
    public RemoteResponse sendCommand(RemoteCommand command) {
        if (!connected) {
            if (!connect()) {
                return new RemoteResponse(RemoteResponse.ResponseStatus.SERVICE_UNAVAILABLE, 
                    "Server unavailable");
            }
        }
        
        try {
            command.setSessionId(sessionId);
            outputStream.writeObject(command);
            outputStream.flush();
            
            Object response = inputStream.readObject();
            if (response instanceof RemoteResponse) {
                return (RemoteResponse) response;
            }
        } catch (SocketException e) {
            System.err.println("[CLIENT] Connection lost, attempting reconnect...");
            connected = false;
            return sendCommand(command); // Retry with reconnect
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[CLIENT] Error sending command: " + e.getMessage());
            connected = false;
        }
        
        return new RemoteResponse(RemoteResponse.ResponseStatus.SERVER_ERROR, "Communication error");
    }
    
    /**
     * Login to server
     */
    public boolean login(String username, String password) {
        if (!connected && !connect()) {
            return false;
        }
        
        RemoteCommand loginCmd = new RemoteCommand(CommandType.LOGIN);
        java.util.Map<String, String> credentials = new java.util.HashMap<>();
        credentials.put("username", username);
        credentials.put("password", password);
        loginCmd.setCommandData(credentials);
        
        RemoteResponse response = sendCommand(loginCmd);
        if (response.isSuccess()) {
            sessionId = (String) response.getResponseData();
            System.out.println("[CLIENT] Logged in successfully");
            return true;
        }
        return false;
    }
    
    /**
     * Get product by ID
     */
    public RemoteResponse getProduct(int productId) {
        RemoteCommand cmd = new RemoteCommand(CommandType.GET_PRODUCT, productId);
        return sendCommand(cmd);
    }
    
    /**
     * Get all products
     */
    public RemoteResponse getAllProducts() {
        RemoteCommand cmd = new RemoteCommand(CommandType.GET_ALL_PRODUCTS);
        return sendCommand(cmd);
    }
    
    /**
     * Disconnect from server
     */
    public void disconnect() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            connected = false;
            System.out.println("[CLIENT] Disconnected from server");
        } catch (IOException e) {
            System.err.println("[CLIENT] Error disconnecting: " + e.getMessage());
        }
    }
    
    /**
     * Check connection status
     */
    public boolean isConnected() {
        return connected && socket != null && socket.isConnected();
    }
}
