package core.service;

import core.dao.AlertDAO;
import core.model.StockAlert;
import java.util.*;

/**
 * AlertService: Stock alert management and notifications
 */
public class AlertService extends BaseService {
    private AlertDAO alertDAO;
    
    public AlertService() {
        this.alertDAO = new AlertDAO();
    }
    
    /**
     * Create a stock alert
     */
    public int createAlert(StockAlert alert) {
        if (!isValid(alert) || alert.getProductId() <= 0) {
            return -1;
        }
        return alertDAO.create(alert);
    }
    
    /**
     * Get all pending alerts
     */
    public List<StockAlert> getPendingAlerts() {
        return alertDAO.getAll();
    }
    
    /**
     * Get alert by ID
     */
    public StockAlert getAlert(int alertId) {
        return alertDAO.read(alertId);
    }
    
    /**
     * Acknowledge alert
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
     * Resolve alert
     */
    public boolean resolveAlert(int alertId) {
        return alertDAO.delete(alertId);
    }
    
    /**
     * Send notification for critical alerts
     */
    public void notifyCriticalAlert(StockAlert alert) {
        if ("HIGH".equals(alert.getSeverity())) {
            logInfo("CRITICAL ALERT: " + alert.getProductName() + " stock: " + alert.getCurrentStock());
            // Implement notification mechanism (email, SMS, etc.)
        }
    }
    
    /**
     * Check and generate alerts based on current inventory
     */
    public void checkInventoryAlerts(int productId, int currentStock, int minStock) {
        if (currentStock <= minStock) {
            StockAlert alert = new StockAlert();
            alert.setProductId(productId);
            alert.setCurrentStock(currentStock);
            alert.setThreshold(minStock);
            alert.setAlertType(StockAlert.AlertType.LOW_STOCK);
            alert.setSeverity(currentStock == 0 ? "HIGH" : "MEDIUM");
            
            int alertId = createAlert(alert);
            if (alertId > 0) {
                notifyCriticalAlert(alert);
            }
        }
    }
}
