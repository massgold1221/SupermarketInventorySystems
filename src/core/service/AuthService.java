package core.service;

import core.dao.UserDAO;
import core.model.User;
import java.util.*;

/**
 * AuthService: Authentication and authorization service
 */
public class AuthService extends BaseService {
    private UserDAO userDAO;
    
    public AuthService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Authenticate user with username and password
     */
    public User authenticate(String username, String password) {
        if (!isValidString(username) || !isValidString(password)) {
            return null;
        }
        
        User user = userDAO.findByUsername(username);
        if (user == null || user.isLocked()) {
            return null;
        }
        
        // In real implementation, use secure password hashing (BCrypt, Argon2, etc.)
        if (verifyPassword(password, user.getPasswordHash())) {
            return user;
        }
        
        // Increment failed login attempts
        user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
        if (user.getFailedLoginAttempts() >= 5) {
            user.setLocked(true);
        }
        userDAO.update(user);
        return null;
    }
    
    /**
     * Create a new user
     */
    public int createUser(User user) {
        if (!isValidString(user.getUsername()) || !isValidString(user.getPasswordHash())) {
            return -1;
        }
        return userDAO.create(user);
    }
    
    /**
     * Update user
     */
    public boolean updateUser(User user) {
        return isValid(user) && userDAO.update(user);
    }
    
    /**
     * Get user by ID
     */
    public User getUser(int userId) {
        return userDAO.read(userId);
    }
    
    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        return userDAO.getAll();
    }
    
    /**
     * Check user permissions
     */
    public boolean hasPermission(User user, String permission) {
        if (user == null) {
            return false;
        }
        
        switch (user.getRole()) {
            case ADMIN:
                return true;
            case MANAGER:
                return !permission.contains("admin");
            case STAFF:
                return permission.contains("basic");
            default:
                return false;
        }
    }
    
    /**
     * Verify password against hash
     */
    private boolean verifyPassword(String password, String hash) {
        // Implement proper password verification (BCrypt, etc.)
        // For now, simple comparison (NOT FOR PRODUCTION)
        return password.equals(hash);
    }
    
    /**
     * Unlock a user account
     */
    public boolean unlockUser(int userId) {
        User user = userDAO.read(userId);
        if (user == null) {
            return false;
        }
        user.setLocked(false);
        user.setFailedLoginAttempts(0);
        return userDAO.update(user);
    }
}
