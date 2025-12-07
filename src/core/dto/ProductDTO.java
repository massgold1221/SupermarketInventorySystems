package core.dto;

import java.io.Serializable;

/**
 * ProductDTO: Data Transfer Object for Product
 */
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String productCode;
    private String productName;
    private double unitPrice;
    private int currentStock;
    private int minStock;
    private String categoryName;
    
    public ProductDTO() {}
    
    public ProductDTO(int id, String productCode, String productName, double unitPrice, int currentStock) {
        this.id = id;
        this.productCode = productCode;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.currentStock = currentStock;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getProductCode() {
        return productCode;
    }
    
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public int getCurrentStock() {
        return currentStock;
    }
    
    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }
    
    public int getMinStock() {
        return minStock;
    }
    
    public void setMinStock(int minStock) {
        this.minStock = minStock;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
