package core.service;

import core.dao.*;
import core.model.*;
import java.util.*;

/**
 * InventoryService: Business logic layer for inventory management
 * Handles product management, stock updates, and inventory operations
 */
public class InventoryService extends BaseService {
    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;
    private SupplierDAO supplierDAO;
    private TransactionDAO transactionDAO;
    private AlertDAO alertDAO;
    
    public InventoryService() {
        this.productDAO = new ProductDAO();
        this.categoryDAO = new CategoryDAO();
        this.supplierDAO = new SupplierDAO();
        this.transactionDAO = new TransactionDAO();
        this.alertDAO = new AlertDAO();
    }
    
    /**
     * Add a new product to inventory
     */
    public int addProduct(Product product) {
        if (!isValidString(product.getProductName()) || !isPositive(product.getUnitPrice())) {
            logError("Invalid product data", new IllegalArgumentException("Invalid product"));
            return -1;
        }
        return productDAO.create(product);
    }
    
    /**
     * Update product information
     */
    public boolean updateProduct(Product product) {
        if (!isValid(product) || product.getId() <= 0) {
            return false;
        }
        return productDAO.update(product);
    }
    
    /**
     * Get product by ID
     */
    public Product getProduct(int productId) {
        return productDAO.read(productId);
    }
    
    /**
     * Get all products
     */
    public List<Product> getAllProducts() {
        return productDAO.getAll();
    }
    
    /**
     * Search products
     */
    public List<Product> searchProducts(String query) {
        if (!isValidString(query)) {
            return new ArrayList<>();
        }
        return productDAO.search(query);
    }
    
    /**
     * Update stock level
     */
    public boolean updateStock(int productId, int newQuantity) {
        Product product = productDAO.read(productId);
        if (product == null) {
            return false;
        }
        product.setCurrentStock(newQuantity);
        return productDAO.update(product);
    }
    
    /**
     * Check if product is low on stock
     */
    public boolean isLowStock(int productId) {
        Product product = productDAO.read(productId);
        if (product == null) {
            return false;
        }
        return product.isLowStock();
    }
    
    /**
     * Record an inventory transaction
     */
    public int recordTransaction(InventoryTransaction transaction) {
        if (!isValid(transaction) || transaction.getProductId() <= 0) {
            return -1;
        }
        
        // Update product stock based on transaction type
        Product product = productDAO.read(transaction.getProductId());
        if (product == null) {
            return -1;
        }
        
        switch (transaction.getTransactionType()) {
            case PURCHASE:
                product.setCurrentStock(product.getCurrentStock() + transaction.getQuantity());
                break;
            case SALE:
            case RETURN:
                product.setCurrentStock(product.getCurrentStock() - transaction.getQuantity());
                break;
            default:
                break;
        }
        
        productDAO.update(product);
        
        // Create alert if stock is critical
        if (product.isLowStock()) {
            createStockAlert(product);
        }
        
        return transactionDAO.create(transaction);
    }
    
    /**
     * Create a stock alert
     */
    private void createStockAlert(Product product) {
        StockAlert alert = new StockAlert();
        alert.setProductId(product.getId());
        alert.setProductName(product.getProductName());
        alert.setAlertType(StockAlert.AlertType.LOW_STOCK);
        alert.setCurrentStock(product.getCurrentStock());
        alert.setThreshold(product.getMinStock());
        alert.setSeverity("MEDIUM");
        
        alertDAO.create(alert);
    }
    
    /**
     * Get pending alerts
     */
    public List<StockAlert> getPendingAlerts() {
        return alertDAO.getAll();
    }
    
    /**
     * Acknowledge an alert
     */
    public boolean acknowledgeAlert(int alertId, int userId) {
        StockAlert alert = alertDAO.read(alertId);
        if (alert == null) {
            return false;
        }
        alert.setAcknowledged(true);
        alert.setAcknowledgedBy(userId);
        alert.setAcknowledgedAt(java.time.LocalDateTime.now());
        return alertDAO.update(alert);
    }
    
    /**
     * Get category by ID
     */
    public Category getCategory(int categoryId) {
        return categoryDAO.read(categoryId);
    }
    
    /**
     * Get all categories
     */
    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }
    
    /**
     * Add a new category
     */
    public int addCategory(Category category) {
        return categoryDAO.create(category);
    }
}
