package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigLoader: Configuration management for the application
 */
public class ConfigLoader {
    private static final String DB_CONFIG_FILE = "config/database.properties";
    private static final String APP_CONFIG_FILE = "config/application.properties";
    private static final String SERVER_CONFIG_FILE = "config/server.properties";
    
    private static Properties dbProperties;
    private static Properties appProperties;
    private static Properties serverProperties;
    
    static {
        loadConfigurations();
    }
    
    /**
     * Load all configuration files
     */
    private static void loadConfigurations() {
        dbProperties = loadProperties(DB_CONFIG_FILE);
        appProperties = loadProperties(APP_CONFIG_FILE);
        serverProperties = loadProperties(SERVER_CONFIG_FILE);
    }
    
    /**
     * Load a properties file
     */
    private static Properties loadProperties(String filePath) {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            props.load(fis);
        } catch (IOException e) {
            Logger.warn("Could not load properties from " + filePath + ": " + e.getMessage());
        }
        return props;
    }
    
    /**
     * Get database configuration
     */
    public static String getDatabaseUrl() {
        return dbProperties.getProperty("db.url", "jdbc:mysql://localhost:3306/supermarket_inventory");
    }
    
    public static String getDatabaseUser() {
        return dbProperties.getProperty("db.user", "root");
    }
    
    public static String getDatabasePassword() {
        return dbProperties.getProperty("db.password", "");
    }
    
    public static int getDatabasePoolSize() {
        return Integer.parseInt(dbProperties.getProperty("db.pool.size", "10"));
    }
    
    /**
     * Get application configuration
     */
    public static String getApplicationName() {
        return appProperties.getProperty("app.name", "Supermarket Inventory System");
    }
    
    public static String getApplicationVersion() {
        return appProperties.getProperty("app.version", "1.0.0");
    }
    
    /**
     * Get server configuration
     */
    public static int getServerPort() {
        return Integer.parseInt(serverProperties.getProperty("server.port", "5000"));
    }
    
    public static String getServerHost() {
        return serverProperties.getProperty("server.host", "localhost");
    }
    
    public static int getServerThreadPoolSize() {
        return Integer.parseInt(serverProperties.getProperty("server.thread.pool.size", "20"));
    }
}
