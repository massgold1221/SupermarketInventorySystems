package core.service;

/**
 * BaseService: Common service methods
 * Provides shared business logic and error handling
 */
public abstract class BaseService {
    
    /**
     * Validate if an object is not null
     */
    protected boolean isValid(Object obj) {
        return obj != null;
    }
    
    /**
     * Validate if a string is not empty
     */
    protected boolean isValidString(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Validate if a double value is positive
     */
    protected boolean isPositive(double value) {
        return value > 0;
    }
    
    /**
     * Log an error message
     */
    protected void logError(String message, Exception e) {
        System.err.println("[ERROR] " + message + ": " + e.getMessage());
        e.printStackTrace();
    }
    
    /**
     * Log an info message
     */
    protected void logInfo(String message) {
        System.out.println("[INFO] " + message);
    }
}
