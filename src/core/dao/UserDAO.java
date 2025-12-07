package core.dao;

import core.model.User;
import java.sql.*;
import java.util.*;

/**
 * UserDAO: Data access layer for User entities
 */
public class UserDAO extends BaseDAO<User> {
    private Connection connection;
    
    public UserDAO() {
        try {
            this.connection = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            System.err.println("Error initializing UserDAO: " + e.getMessage());
        }
    }
    
    @Override
    public int create(User user) {
        String sql = "INSERT INTO users (username, password_hash, full_name, email, phone, role, department) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getRole().toString());
            stmt.setString(7, user.getDepartment());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        return -1;
    }
    
    @Override
    public User read(int id) {
        String sql = "SELECT * FROM users WHERE id = ? AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error reading user: " + e.getMessage());
        }
        return null;
    }
    
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ? AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToUser(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by username: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public boolean update(User user) {
        String sql = "UPDATE users SET username = ?, password_hash = ?, full_name = ?, email = ?, " +
                     "phone = ?, role = ?, department = ?, locked = ? WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setString(3, user.getFullName());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getPhone());
            stmt.setString(6, user.getRole().toString());
            stmt.setString(7, user.getDepartment());
            stmt.setBoolean(8, user.isLocked());
            stmt.setInt(9, user.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean delete(int id) {
        String sql = "UPDATE users SET is_active = 0 WHERE id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE is_active = 1";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }
    
    @Override
    public List<User> search(String query) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE (username LIKE ? OR full_name LIKE ?) AND is_active = 1";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            String searchPattern = "%" + query + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapResultSetToUser(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error searching users: " + e.getMessage());
        }
        return users;
    }
    
    @Override
    public int[] createBatch(List<User> users) {
        return new int[0];
    }
    
    @Override
    public boolean updateBatch(List<User> users) {
        return false;
    }
    
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setFullName(rs.getString("full_name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setRole(User.UserRole.valueOf(rs.getString("role")));
        user.setDepartment(rs.getString("department"));
        user.setLocked(rs.getBoolean("locked"));
        return user;
    }
}
