package network.server;

import network.protocol.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * InventoryServer: Main server for remote inventory access
 * Enhanced with graceful shutdown capability
 */
public class InventoryServer {
    private ServerSocket serverSocket;
    private int port;
    private boolean running;
    private ExecutorService executorService;
    private SessionManager sessionManager;
    private static final int THREAD_POOL_SIZE = 20;
    
    public InventoryServer(int port) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.sessionManager = new SessionManager();
        this.running = false;
    }
    
    /**
     * Start the server
     */
    public void start() {
        try {
            serverSocket = new ServerSocket(port);
            running = true;
            System.out.println("[SERVER] Inventory Server started on port " + port);
            
            // Shutdown hook for graceful shutdown
            Runtime.getRuntime().addShutdownHook(new Thread(this::shutdown));
            
            // Accept client connections
            while (running) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("[SERVER] New client connected from " + clientSocket.getInetAddress());
                    
                    ClientHandler handler = new ClientHandler(clientSocket, sessionManager);
                    executorService.execute(handler);
                } catch (SocketException e) {
                    if (running) {
                        System.err.println("[SERVER] Socket error: " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("[SERVER] Error starting server: " + e.getMessage());
        }
    }
    
    /**
     * Stop the server gracefully
     */
    public synchronized void shutdown() {
        if (!running) {
            return;
        }
        
        running = false;
        System.out.println("[SERVER] Initiating graceful shutdown...");
        
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("[SERVER] Error closing server socket: " + e.getMessage());
        }
        
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[SERVER] Server shutdown complete");
    }
    
    /**
     * Check if server is running
     */
    public boolean isRunning() {
        return running;
    }
    
    /**
     * Get server statistics
     */
    public String getServerStats() {
        return String.format("Server Port: %d, Active Sessions: %d", 
            port, sessionManager.getActiveSessions());
    }
    
    public static void main(String[] args) {
        int port = 5000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        
        InventoryServer server = new InventoryServer(port);
        server.start();
    }
}
