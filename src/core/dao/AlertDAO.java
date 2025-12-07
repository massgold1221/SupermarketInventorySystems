package core.dao;

import core.model.StockAlert;
import java.sql.*;
import java.util.*;

/**
 * AlertDAO: Data access layer for Stock Alerts
 */
public class AlertDAO extends BaseDAO<StockAlert> {
    private Connection connection;
    
    public AlertDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing AlertDAO: " + e.getMessage());
        }
    }
    
    @Override
    public int create(StockAlert alert) {
        String sql = "INSERT INTO stock_alerts (product_id, product_name, alert_type, current_stock, threshold, severity) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, alert.getProductId());
            stmt.setString(2, alert.getProductName());
            stmt.setString(3, alert.getAlertType().toString());
            stmt.setInt(4, alert.getCurrentStock());
            stmt.setInt(5, alert.getThreshold());
            stmt.setString(6, alert.getSeverity());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating alert: " + e.getMessage());
        }
        return -1;
    }
    
    @Override
    public StockAlert read(int id) {
        String sql = "SELECT * FROM stock_alerts WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAlert(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading alert: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public boolean update(StockAlert alert) {
        String sql = "UPDATE stock_alerts SET acknowledged = ?, acknowledged_by = ?, acknowledged_at = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, alert.isAcknowledged());
            stmt.setInt(2, alert.getAcknowledgedBy());
            stmt.setTimestamp(3, alert.getAcknowledgedAt() != null ? Timestamp.valueOf(alert.getAcknowledgedAt()) : null);
            stmt.setInt(4, alert.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating alert: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM stock_alerts WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting alert: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public List<StockAlert> getAll() {
        List<StockAlert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM stock_alerts WHERE acknowledged = 0 ORDER BY created_at DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                alerts.add(mapResultSetToAlert(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all alerts: " + e.getMessage());
        }
        return alerts;
    }
    
    @Override
    public List<StockAlert> search(String query) {
        List<StockAlert> alerts = new ArrayList<>();
        String sql = "SELECT * FROM stock_alerts WHERE product_name LIKE ? AND acknowledged = 0";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    alerts.add(mapResultSetToAlert(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching alerts: " + e.getMessage());
        }
        return alerts;
    }
    
    @Override
    public int[] createBatch(List<StockAlert> alerts) {
        return new int[0];
    }
    
    @Override
    public boolean updateBatch(List<StockAlert> alerts) {
        return false;
    }
    
    private StockAlert mapResultSetToAlert(ResultSet rs) throws SQLException {
        StockAlert alert = new StockAlert();
        alert.setId(rs.getInt("id"));
        alert.setProductId(rs.getInt("product_id"));
        alert.setProductName(rs.getString("product_name"));
        alert.setAlertType(StockAlert.AlertType.valueOf(rs.getString("alert_type")));
        alert.setCurrentStock(rs.getInt("current_stock"));
        alert.setThreshold(rs.getInt("threshold"));
        alert.setSeverity(rs.getString("severity"));
        alert.setAcknowledged(rs.getBoolean("acknowledged"));
        alert.setAcknowledgedBy(rs.getInt("acknowledged_by"));
        Timestamp acknowledgedAt = rs.getTimestamp("acknowledged_at");
        if (acknowledgedAt != null) {
            alert.setAcknowledgedAt(acknowledgedAt.toLocalDateTime());
        }
        alert.setRemarks(rs.getString("remarks"));
        return alert;
    }
}
