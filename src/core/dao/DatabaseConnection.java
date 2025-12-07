package core.dao;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * DatabaseConnection: Manages database connections with connection pooling
 * Configurable pooling options for performance optimization
 */
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private String url;
    private String user;
    private String password;
    private int poolSize;
    
    private DatabaseConnection() {
        loadDatabaseProperties();
    }
    
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    private void loadDatabaseProperties() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("config/database.properties"));
            
            this.url = properties.getProperty("db.url", "jdbc:mysql://localhost:3306/supermarket_inventory");
            this.user = properties.getProperty("db.user", "root");
            this.password = properties.getProperty("db.password", "");
            this.poolSize = Integer.parseInt(properties.getProperty("db.pool.size", "10"));
            
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading database properties: " + e.getMessage());
        }
    }
    
    /**
     * Get a database connection
     */
    public synchronized Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection: " + e.getMessage());
            throw e;
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public synchronized void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
    
    /**
     * Test the connection
     */
    public boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Connection test failed: " + e.getMessage());
            return false;
        }
    }
}
