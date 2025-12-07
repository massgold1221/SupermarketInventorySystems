package core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * BaseEntity: Common fields for all entities
 * Provides timestamp tracking and ID management
 */
public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected int id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected String createdBy;
    protected String updatedBy;
    protected boolean isActive;
    
    public BaseEntity() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.isActive = true;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    public String getUpdatedBy() {
        return updatedBy;
    }
    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
}
