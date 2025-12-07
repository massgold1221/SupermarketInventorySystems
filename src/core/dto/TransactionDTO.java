package core.dto;

import java.io.Serializable;

/**
 * TransactionDTO: Data Transfer Object for Inventory Transactions
 */
public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double totalValue;
    private String transactionType;
    private String transactionDate;
    
    public TransactionDTO() {}
    
    public TransactionDTO(int productId, String productName, int quantity, String transactionType) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.transactionType = transactionType;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
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
    
    public String getTransactionType() {
        return transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
    
    public String getTransactionDate() {
        return transactionDate;
    }
    
    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
