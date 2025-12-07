package core.model;

/**
 * Product: Represents a product in the supermarket inventory
 * Enhanced with validation rules
 */
public class Product extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    private String productCode;
    private String productName;
    private String description;
    private double unitPrice;
    private int categoryId;
    private int currentStock;
    private int minStock;
    private int maxStock;
    private String unit;
    private int supplierId;
    private String barcode;
    private boolean discontinued;
    
    public Product() {
        super();
    }
    
    public Product(String productCode, String productName, double unitPrice) {
        super();
        this.productCode = productCode;
        this.productName = productName;
        this.unitPrice = unitPrice;
    }
    
    // Validation methods
    public boolean isValidPrice() {
        return unitPrice > 0;
    }
    
    public boolean isValidStock() {
        return currentStock >= 0 && minStock >= 0 && maxStock >= minStock;
    }
    
    public boolean isLowStock() {
        return currentStock <= minStock;
    }
    
    public boolean isOverstock() {
        return currentStock >= maxStock;
    }
    
    // Getters and Setters
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    
    public int getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
    
    public int getMaxStock() {
        return maxStock;
    }
    
    public void setMaxStock(int maxStock) {
        this.maxStock = maxStock;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public int getSupplierId() {
        return supplierId;
    }
    
    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public boolean isDiscontinued() {
        return discontinued;
    }
    
    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }
}
