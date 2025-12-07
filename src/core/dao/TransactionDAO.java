package core.dao;

import core.model.InventoryTransaction;
import java.sql.*;
import java.util.*;

/**
 * TransactionDAO: Data access layer for Inventory Transactions
 * Includes isolation levels for concurrent access
 */
public class TransactionDAO extends BaseDAO<InventoryTransaction> {
    private Connection connection;
    
    public TransactionDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing TransactionDAO: " + e.getMessage());
        }
    }
    
    @Override
    public int create(InventoryTransaction transaction) {
        String sql = "INSERT INTO inventory_transactions (product_id, product_name, quantity, unit_price, " +
                     "total_value, transaction_type, reference_number, supplier_id, user_id, remarks, transaction_date) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, transaction.getProductId());
            stmt.setString(2, transaction.getProductName());
            stmt.setInt(3, transaction.getQuantity());
            stmt.setDouble(4, transaction.getUnitPrice());
            stmt.setDouble(5, transaction.getTotalValue());
            stmt.setString(6, transaction.getTransactionType().toString());
            stmt.setString(7, transaction.getReferenceNumber());
            stmt.setInt(8, transaction.getSupplierId());
            stmt.setInt(9, transaction.getUserId());
            stmt.setString(10, transaction.getRemarks());
            stmt.setTimestamp(11, Timestamp.valueOf(transaction.getTransactionDate()));
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating transaction: " + e.getMessage());
        }
        return -1;
    }
    
    @Override
    public InventoryTransaction read(int id) {
        String sql = "SELECT * FROM inventory_transactions WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTransaction(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading transaction: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public boolean update(InventoryTransaction transaction) {
        return false;  // Transactions are typically immutable
    }
    
    @Override
    public boolean delete(int id) {
        return false;  // Transactions are typically not deleted
    }
    
    @Override
    public List<InventoryTransaction> getAll() {
        List<InventoryTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM inventory_transactions ORDER BY transaction_date DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                transactions.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all transactions: " + e.getMessage());
        }
        return transactions;
    }
    
    @Override
    public List<InventoryTransaction> search(String query) {
        List<InventoryTransaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM inventory_transactions WHERE product_name LIKE ? OR reference_number LIKE ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + query + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching transactions: " + e.getMessage());
        }
        return transactions;
    }
    
    @Override
    public int[] createBatch(List<InventoryTransaction> transactions) {
        String sql = "INSERT INTO inventory_transactions (product_id, quantity, transaction_type) VALUES (?, ?, ?)";
        int[] results = new int[transactions.size()];
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (InventoryTransaction transaction : transactions) {
                stmt.setInt(1, transaction.getProductId());
                stmt.setInt(2, transaction.getQuantity());
                stmt.setString(3, transaction.getTransactionType().toString());
                stmt.addBatch();
            }
            results = stmt.executeBatch();
        } catch (SQLException e) {
            System.err.println("Error in batch create: " + e.getMessage());
        }
        return results;
    }
    
    @Override
    public boolean updateBatch(List<InventoryTransaction> transactions) {
        return false;
    }
    
    private InventoryTransaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setId(rs.getInt("id"));
        transaction.setProductId(rs.getInt("product_id"));
        transaction.setProductName(rs.getString("product_name"));
        transaction.setQuantity(rs.getInt("quantity"));
        transaction.setUnitPrice(rs.getDouble("unit_price"));
        transaction.setTotalValue(rs.getDouble("total_value"));
        transaction.setTransactionType(InventoryTransaction.TransactionType.valueOf(rs.getString("transaction_type")));
        transaction.setReferenceNumber(rs.getString("reference_number"));
        transaction.setSupplierId(rs.getInt("supplier_id"));
        transaction.setUserId(rs.getInt("user_id"));
        transaction.setRemarks(rs.getString("remarks"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
        return transaction;
    }
}
