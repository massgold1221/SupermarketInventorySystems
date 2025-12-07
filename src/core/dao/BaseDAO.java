package core.dao;

import java.util.List;

/**
 * BaseDAO: Template pattern for common database operations
 * Provides generic CRUD operations for all entities
 */
public abstract class BaseDAO<T> {
    
    /**
     * Create a new entity in the database
     */
    public abstract int create(T entity);
    
    /**
     * Read an entity by ID
     */
    public abstract T read(int id);
    
    /**
     * Update an existing entity
     */
    public abstract boolean update(T entity);
    
    /**
     * Delete an entity by ID
     */
    public abstract boolean delete(int id);
    
    /**
     * Get all entities
     */
    public abstract List<T> getAll();
    
    /**
     * Search entities based on criteria
     */
    public abstract List<T> search(String query);
    
    /**
     * Batch insert operations
     */
    public abstract int[] createBatch(List<T> entities);
    
    /**
     * Batch update operations
     */
    public abstract boolean updateBatch(List<T> entities);
}
