package core.model;

import java.time.LocalDateTime;

/**
 * InventoryTransaction: Tracks all inventory movements
 */
public class InventoryTransaction extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    public enum TransactionType {
        PURCHASE, SALE, ADJUSTMENT, RETURN, TRANSFER, DAMAGE
    }
    
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalValue;
    private TransactionType transactionType;
    private String referenceNumber;
    private int supplierId;
    private int userId;
    private String remarks;
    private LocalDateTime transactionDate;
    
    public InventoryTransaction() {
        super();
        this.transactionDate = LocalDateTime.now();
    }
    
    public InventoryTransaction(int productId, int quantity, TransactionType type) {
        super();
        this.productId = productId;
        this.quantity = quantity;
        this.transactionType = type;
        this.transactionDate = LocalDateTime.now();
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
    
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public double getTotalValue() {
        return totalValue;
    }
    
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
    
    public TransactionType getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
    
    public String getReferenceNumber() {
        return referenceNumber;
    }
    
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
    
    public int getSupplierId() {
        return supplierId;
    }
    
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
