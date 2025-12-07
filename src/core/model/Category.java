package core.model;

/**
 * Category: Product categories for organization
 */
public class Category extends BaseEntity {
    private static final long serialVersionUID = 1L;
    
    private String categoryName;
    private String description;
    private String categoryCode;
    
    public Category() {
        super();
    }
    
    public Category(String categoryName) {
        super();
        this.categoryName = categoryName;
    }
    
    // Getters and Setters
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCategoryCode() {
        return categoryCode;
    }
    
    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    
    @Override
    public String toString() {
        return categoryName;
    }
}
