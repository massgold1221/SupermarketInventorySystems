package core.dao;

import core.model.Supplier;
import java.sql.*;
import java.util.*;

/**
 * SupplierDAO: Data access layer for Supplier entities
 */
public class SupplierDAO extends BaseDAO<Supplier> {
    private Connection connection;
    
    public SupplierDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing SupplierDAO: " + e.getMessage());
        }
    }
    
    @Override
    public int create(Supplier supplier) {
        String sql = "INSERT INTO suppliers (supplier_name, contact_person, email, phone, address, " +
                     "city, country, credit_limit, current_debt, payment_terms_days) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getEmail());
            stmt.setString(4, supplier.getPhone());
            stmt.setString(5, supplier.getAddress());
            stmt.setString(6, supplier.getCity());
            stmt.setString(7, supplier.getCountry());
            stmt.setDouble(8, supplier.getCreditLimit());
            stmt.setDouble(9, supplier.getCurrentDebt());
            stmt.setInt(10, supplier.getPaymentTermsDays());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating supplier: " + e.getMessage());
        }
        return -1;
    }
    
    @Override
    public Supplier read(int id) {
        String sql = "SELECT * FROM suppliers WHERE id = ? AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToSupplier(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading supplier: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public boolean update(Supplier supplier) {
        String sql = "UPDATE suppliers SET supplier_name = ?, contact_person = ?, email = ?, phone = ?, " +
                     "address = ?, city = ?, country = ?, credit_limit = ?, current_debt = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getContactPerson());
            stmt.setString(3, supplier.getEmail());
            stmt.setString(4, supplier.getPhone());
            stmt.setString(5, supplier.getAddress());
            stmt.setString(6, supplier.getCity());
            stmt.setString(7, supplier.getCountry());
            stmt.setDouble(8, supplier.getCreditLimit());
            stmt.setDouble(9, supplier.getCurrentDebt());
            stmt.setInt(10, supplier.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating supplier: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "UPDATE suppliers SET is_active = 0 WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting supplier: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers WHERE is_active = 1";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                suppliers.add(mapResultSetToSupplier(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all suppliers: " + e.getMessage());
        }
        return suppliers;
    }
    
    @Override
    public List<Supplier> search(String query) {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers WHERE supplier_name LIKE ? AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    suppliers.add(mapResultSetToSupplier(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching suppliers: " + e.getMessage());
        }
        return suppliers;
    }
    
    @Override
    public int[] createBatch(List<Supplier> suppliers) {
        return new int[0];
    }
    
    @Override
    public boolean updateBatch(List<Supplier> suppliers) {
        return false;
    }
    
    private Supplier mapResultSetToSupplier(ResultSet rs) throws SQLException {
        Supplier supplier = new Supplier();
        supplier.setId(rs.getInt("id"));
        supplier.setSupplierName(rs.getString("supplier_name"));
        supplier.setContactPerson(rs.getString("contact_person"));
        supplier.setEmail(rs.getString("email"));
        supplier.setPhone(rs.getString("phone"));
        supplier.setAddress(rs.getString("address"));
        supplier.setCity(rs.getString("city"));
        supplier.setCountry(rs.getString("country"));
        supplier.setCreditLimit(rs.getDouble("credit_limit"));
        supplier.setCurrentDebt(rs.getDouble("current_debt"));
        supplier.setPaymentTermsDays(rs.getInt("payment_terms_days"));
        return supplier;
    }
}
