import util.*;
import network.server.InventoryServer;
import concurrent.TaskScheduler;

/**
 * MainApp: Application entry point for the Supermarket Inventory System
 */
public class MainApp {
    
    public static void main(String[] args) {
        try {
            // Initialize configuration
            Logger.info("========================================");
            Logger.info(ConfigLoader.getApplicationName() + " v" + ConfigLoader.getApplicationVersion());
            Logger.info("========================================");
            
            // Create necessary directories
            FileUtil.createDirectory("logs");
            FileUtil.createDirectory("reports/generated");
            FileUtil.createDirectory("exports");
            FileUtil.createDirectory("backups");
            
            // Determine running mode
            String mode = args.length > 0 ? args[0] : "server";
            
            if ("server".equalsIgnoreCase(mode)) {
                startServer();
            } else if ("client".equalsIgnoreCase(mode)) {
                startClient();
            } else {
                showUsage();
            }
        } catch (Exception e) {
            Logger.fatal("Application startup failed");
            Logger.error("Fatal error", e);
            System.exit(1);
        }
    }
    
    /**
     * Start the server mode
     */
    private static void startServer() {
        Logger.info("Starting in SERVER mode");
        
        int port = ConfigLoader.getServerPort();
        InventoryServer server = new InventoryServer(port);
        
        // Initialize scheduled tasks
        TaskScheduler scheduler = new TaskScheduler();
        scheduler.scheduleInventoryCheck(5);     // Every 5 minutes
        scheduler.scheduleReportGeneration(24);  // Every 24 hours
        scheduler.scheduleAlertNotifications(30);// Every 30 seconds
        
        Logger.info("Server configured to run on port: " + port);
        Logger.info("Starting server...");
        
        // Start server (blocking call)
        server.start();
    }
    
    /**
     * Start the client mode
     */
    private static void startClient() {
        Logger.info("Starting in CLIENT mode");
        Logger.info("Client UI initialization not yet implemented");
        Logger.info("Use InventoryClient for programmatic access");
    }
    
    /**
     * Show usage information
     */
    private static void showUsage() {
        System.out.println("Usage: java MainApp [mode]");
        System.out.println("Modes:");
        System.out.println("  server - Start the inventory server");
        System.out.println("  client - Start the client application");
        System.out.println("Default: server");
    }
}
