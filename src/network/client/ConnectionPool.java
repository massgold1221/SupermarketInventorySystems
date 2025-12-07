package network.client;

import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;

/**
 * ConnectionPool: Client-side connection pool for managing multiple connections
 */
public class ConnectionPool {
    private String serverHost;
    private int serverPort;
    private Queue<InventoryClient> availableConnections;
    private Set<InventoryClient> usedConnections;
    private int maxPoolSize;
    private static final int DEFAULT_MAX_POOL_SIZE = 10;
    
    public ConnectionPool(String serverHost, int serverPort) {
        this(serverHost, serverPort, DEFAULT_MAX_POOL_SIZE);
    }
    
    public ConnectionPool(String serverHost, int serverPort, int maxPoolSize) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
        this.maxPoolSize = maxPoolSize;
        this.availableConnections = new ConcurrentLinkedQueue<>();
        this.usedConnections = Collections.synchronizedSet(new HashSet<>());
        
        // Initialize pool with minimum connections
        initializePool();
    }
    
    /**
     * Initialize the connection pool
     */
    private void initializePool() {
        for (int i = 0; i < maxPoolSize / 2; i++) {
            InventoryClient client = new InventoryClient(serverHost, serverPort);
            if (client.connect()) {
                availableConnections.add(client);
            }
        }
        System.out.println("[POOL] Connection pool initialized with " + availableConnections.size() + " connections");
    }
    
    /**
     * Get a connection from the pool
     */
    public InventoryClient getConnection() {
        InventoryClient client = availableConnections.poll();
        
        if (client == null) {
            // Create new connection if pool is not full
            if (usedConnections.size() < maxPoolSize) {
                client = new InventoryClient(serverHost, serverPort);
                if (client.connect()) {
                    System.out.println("[POOL] Created new connection, total used: " + (usedConnections.size() + 1));
                } else {
                    return null;
                }
            } else {
                System.err.println("[POOL] Maximum pool size reached");
                return null;
            }
        }
        
        usedConnections.add(client);
        return client;
    }
    
    /**
     * Return a connection to the pool
     */
    public void returnConnection(InventoryClient client) {
        if (client != null && client.isConnected()) {
            usedConnections.remove(client);
            availableConnections.offer(client);
        }
    }
    
    /**
     * Close all connections in the pool
     */
    public void closeAll() {
        for (InventoryClient client : availableConnections) {
            client.disconnect();
        }
        for (InventoryClient client : usedConnections) {
            client.disconnect();
        }
        availableConnections.clear();
        usedConnections.clear();
        System.out.println("[POOL] Connection pool closed");
    }
    
    /**
     * Get pool statistics
     */
    public String getPoolStats() {
        return String.format("Available: %d, Used: %d, Max: %d",
            availableConnections.size(), usedConnections.size(), maxPoolSize);
    }
}
