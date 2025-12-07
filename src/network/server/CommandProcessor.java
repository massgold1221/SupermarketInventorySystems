package network.server;

import network.protocol.*;
import core.service.*;
import core.model.*;
import java.util.*;

/**
 * CommandProcessor: Processes remote commands
 */
public class CommandProcessor {
    private SessionManager sessionManager;
    private InventoryService inventoryService;
    private AuthService authService;
    private AlertService alertService;
    
    public CommandProcessor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.inventoryService = new InventoryService();
        this.authService = new AuthService();
        this.alertService = new AlertService();
    }
    
    /**
     * Process incoming command
     */
    public RemoteResponse processCommand(RemoteCommand command) {
        try {
            switch (command.getCommandType()) {
                case LOGIN:
                    return handleLogin(command);
                case GET_PRODUCT:
                    return handleGetProduct(command);
                case GET_ALL_PRODUCTS:
                    return handleGetAllProducts();
                case ADD_PRODUCT:
                    return handleAddProduct(command);
                case UPDATE_STOCK:
                    return handleUpdateStock(command);
                case GET_ALERTS:
                    return handleGetAlerts();
                case PING:
                    return handlePing();
                default:
                    return new RemoteResponse(RemoteResponse.ResponseStatus.BAD_REQUEST, 
                        "Unknown command: " + command.getCommandType());
            }
        } catch (Exception e) {
            return new RemoteResponse(RemoteResponse.ResponseStatus.SERVER_ERROR, 
                "Error processing command", null);
        }
    }
    
    private RemoteResponse handleLogin(RemoteCommand command) {
        // Extract credentials from command data
        Map<String, String> credentials = (Map<String, String>) command.getCommandData();
        String username = credentials.get("username");
        String password = credentials.get("password");
        
        User user = authService.authenticate(username, password);
        if (user != null) {
            String sessionId = sessionManager.createSession(username);
            return new RemoteResponse(RemoteResponse.ResponseStatus.SUCCESS, 
                "Login successful", sessionId);
        } else {
            return new RemoteResponse(RemoteResponse.ResponseStatus.UNAUTHORIZED, 
                "Invalid credentials");
        }
    }
    
    private RemoteResponse handleGetProduct(RemoteCommand command) {
        int productId = (Integer) command.getCommandData();
        Product product = inventoryService.getProduct(productId);
        if (product != null) {
            return new RemoteResponse(RemoteResponse.ResponseStatus.SUCCESS, 
                "Product found", product);
        } else {
            return new RemoteResponse(RemoteResponse.ResponseStatus.NOT_FOUND, 
                "Product not found");
        }
    }
    
    private RemoteResponse handleGetAllProducts() {
        List<Product> products = inventoryService.getAllProducts();
        return new RemoteResponse(RemoteResponse.ResponseStatus.SUCCESS, 
            "Products retrieved", products);
    }
    
    private RemoteResponse handleAddProduct(RemoteCommand command) {
        Product product = (Product) command.getCommandData();
        int productId = inventoryService.addProduct(product);
        if (productId > 0) {
            return new RemoteResponse(RemoteResponse.ResponseStatus.CREATED, 
                "Product added successfully", productId);
        } else {
            return new RemoteResponse(RemoteResponse.ResponseStatus.BAD_REQUEST, 
                "Failed to add product");
        }
    }
    
    private RemoteResponse handleUpdateStock(RemoteCommand command) {
        Map<String, Object> data = (Map<String, Object>) command.getCommandData();
        int productId = (Integer) data.get("productId");
        int quantity = (Integer) data.get("quantity");
        
        if (inventoryService.updateStock(productId, quantity)) {
            return new RemoteResponse(RemoteResponse.ResponseStatus.SUCCESS, 
                "Stock updated successfully");
        } else {
            return new RemoteResponse(RemoteResponse.ResponseStatus.BAD_REQUEST, 
                "Failed to update stock");
        }
    }
    
    private RemoteResponse handleGetAlerts() {
        List<StockAlert> alerts = alertService.getPendingAlerts();
        return new RemoteResponse(RemoteResponse.ResponseStatus.SUCCESS, 
            "Alerts retrieved", alerts);
    }
    
    private RemoteResponse handlePing() {
        return new RemoteResponse(RemoteResponse.ResponseStatus.SUCCESS, 
            "Pong", "Server is active");
    }
}
