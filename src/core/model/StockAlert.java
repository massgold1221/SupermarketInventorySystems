package core.model;

import java.time.LocalDateTime;

/**
 * StockAlert: Alerts for low stock, overstock, or other inventory issues
 */
public class StockAlert extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    public enum AlertType {
        LOW_STOCK, OVERSTOCK, OUT_OF_STOCK, NEAR_EXPIRY, DAMAGED
    }
    
    private int productId;
    private String productName;
    private AlertType alertType;
    private int currentStock;
    private int threshold;
    private String severity;
    private boolean acknowledged;
    private int acknowledgedBy;
    private LocalDateTime acknowledgedAt;
    private String remarks;
    
    public StockAlert() {
        super();
        this.acknowledged = false;
    }
    
    public StockAlert(int productId, AlertType alertType, int currentStock) {
        super();
        this.productId = productId;
        this.alertType = alertType;
        this.currentStock = currentStock;
        this.acknowledged = false;
    }
    
    // Getters and Setters
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public AlertType getAlertType() {
        return alertType;
    }
    
    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }
    
    public int getCurrentStock() {
        return currentStock;
    }
    
    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }
    
    public int getThreshold() {
        return threshold;
    }
    
    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public boolean isAcknowledged() {
        return acknowledged;
    }
    
    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }
    
    public int getAcknowledgedBy() {
        return acknowledgedBy;
    }
    
    public void setAcknowledgedBy(int acknowledgedBy) {
        this.acknowledgedBy = acknowledgedBy;
    }
    
    public LocalDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }
    
    public void setAcknowledgedAt(LocalDateTime acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
