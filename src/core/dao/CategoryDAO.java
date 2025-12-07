package core.dao;

import core.model.Category;
import java.sql.*;
import java.util.*;

/**
 * CategoryDAO: Data access layer for Category entities
 */
public class CategoryDAO extends BaseDAO<Category> {
    private Connection connection;
    
    public CategoryDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing CategoryDAO: " + e.getMessage());
        }
    }
    
    @Override
    public int create(Category category) {
        String sql = "INSERT INTO categories (category_code, category_name, description) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, category.getCategoryCode());
            stmt.setString(2, category.getCategoryName());
            stmt.setString(3, category.getDescription());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating category: " + e.getMessage());
        }
        return -1;
    }
    
    @Override
    public Category read(int id) {
        String sql = "SELECT * FROM categories WHERE id = ? AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategory(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading category: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public boolean update(Category category) {
        String sql = "UPDATE categories SET category_code = ?, category_name = ?, description = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, category.getCategoryCode());
            stmt.setString(2, category.getCategoryName());
            stmt.setString(3, category.getDescription());
            stmt.setInt(4, category.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating category: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "UPDATE categories SET is_active = 0 WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting category: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public List<Category> getAll() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE is_active = 1";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                categories.add(mapResultSetToCategory(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all categories: " + e.getMessage());
        }
        return categories;
    }
    
    @Override
    public List<Category> search(String query) {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE category_name LIKE ? AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    categories.add(mapResultSetToCategory(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching categories: " + e.getMessage());
        }
        return categories;
    }
    
    @Override
    public int[] createBatch(List<Category> categories) {
        // Not typically used for categories, but implemented for completeness
        return new int[0];
    }
    
    @Override
    public boolean updateBatch(List<Category> categories) {
        return false;
    }
    
    private Category mapResultSetToCategory(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setCategoryCode(rs.getString("category_code"));
        category.setCategoryName(rs.getString("category_name"));
        category.setDescription(rs.getString("description"));
        return category;
    }
}
