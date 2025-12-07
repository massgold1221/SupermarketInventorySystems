package core.dao;

import core.model.Product;
import java.sql.*;
import java.util.*;

/**
 * ProductDAO: Data access layer for Product entities
 * Includes batch operations and transaction support
 */
public class ProductDAO extends BaseDAO<Product> {
    private Connection connection;
    
    public ProductDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing ProductDAO: " + e.getMessage());
        }
    }
    
    @Override
    public int create(Product product) {
        String sql = "INSERT INTO products (product_code, product_name, description, unit_price, " +
                     "category_id, current_stock, min_stock, max_stock, unit, supplier_id, barcode) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getUnitPrice());
            stmt.setInt(5, product.getCategoryId());
            stmt.setInt(6, product.getCurrentStock());
            stmt.setInt(7, product.getMinStock());
            stmt.setInt(8, product.getMaxStock());
            stmt.setString(9, product.getUnit());
            stmt.setInt(10, product.getSupplierId());
            stmt.setString(11, product.getBarcode());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating product: " + e.getMessage());
        }
        return -1;
    }
    
    @Override
    public Product read(int id) {
        String sql = "SELECT * FROM products WHERE id = ? AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToProduct(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading product: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public boolean update(Product product) {
        String sql = "UPDATE products SET product_code = ?, product_name = ?, description = ?, " +
                     "unit_price = ?, category_id = ?, current_stock = ?, min_stock = ?, " +
                     "max_stock = ?, unit = ?, supplier_id = ?, barcode = ?, discontinued = ?, " +
                     "updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, product.getProductCode());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getDescription());
            stmt.setDouble(4, product.getUnitPrice());
            stmt.setInt(5, product.getCategoryId());
            stmt.setInt(6, product.getCurrentStock());
            stmt.setInt(7, product.getMinStock());
            stmt.setInt(8, product.getMaxStock());
            stmt.setString(9, product.getUnit());
            stmt.setInt(10, product.getSupplierId());
            stmt.setString(11, product.getBarcode());
            stmt.setBoolean(12, product.isDiscontinued());
            stmt.setInt(13, product.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "UPDATE products SET is_active = 0 WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_active = 1";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                products.add(mapResultSetToProduct(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all products: " + e.getMessage());
        }
        return products;
    }
    
    @Override
    public List<Product> search(String query) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE (product_name LIKE ? OR product_code LIKE ?) AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + query + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    products.add(mapResultSetToProduct(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching products: " + e.getMessage());
        }
        return products;
    }
    
    @Override
    public int[] createBatch(List<Product> products) {
        String sql = "INSERT INTO products (product_code, product_name, description, unit_price, " +
                     "category_id, current_stock, min_stock, max_stock, unit, supplier_id, barcode) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        int[] results = new int[products.size()];
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Product product : products) {
                stmt.setString(1, product.getProductCode());
                stmt.setString(2, product.getProductName());
                stmt.setString(3, product.getDescription());
                stmt.setDouble(4, product.getUnitPrice());
                stmt.setInt(5, product.getCategoryId());
                stmt.setInt(6, product.getCurrentStock());
                stmt.setInt(7, product.getMinStock());
                stmt.setInt(8, product.getMaxStock());
                stmt.setString(9, product.getUnit());
                stmt.setInt(10, product.getSupplierId());
                stmt.setString(11, product.getBarcode());
                stmt.addBatch();
            }
            results = stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error in batch create: " + e.getMessage());
        }
        return results;
    }
    
    @Override
    public boolean updateBatch(List<Product> products) {
        String sql = "UPDATE products SET product_code = ?, product_name = ?, unit_price = ?, " +
                     "current_stock = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (Product product : products) {
                stmt.setString(1, product.getProductCode());
                stmt.setString(2, product.getProductName());
                stmt.setDouble(3, product.getUnitPrice());
                stmt.setInt(4, product.getCurrentStock());
                stmt.setInt(5, product.getId());
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            System.err.println("Error in batch update: " + e.getMessage());
        }
        return false;
    }
    
    private Product mapResultSetToProduct(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setProductCode(rs.getString("product_code"));
        product.setProductName(rs.getString("product_name"));
        product.setDescription(rs.getString("description"));
        product.setUnitPrice(rs.getDouble("unit_price"));
        product.setCategoryId(rs.getInt("category_id"));
        product.setCurrentStock(rs.getInt("current_stock"));
        product.setMinStock(rs.getInt("min_stock"));
        product.setMaxStock(rs.getInt("max_stock"));
        product.setUnit(rs.getString("unit"));
        product.setSupplierId(rs.getInt("supplier_id"));
        product.setBarcode(rs.getString("barcode"));
        product.setDiscontinued(rs.getBoolean("discontinued"));
        return product;
    }
}
