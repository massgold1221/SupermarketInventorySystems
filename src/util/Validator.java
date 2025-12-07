package util;

/**
 * Validator: Enhanced validation utilities
 */
public class Validator {
    
    /**
     * Validate email address
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
    
    /**
     * Validate phone number
     */
    public static boolean isValidPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return phone.matches("^\\d{10,15}$");
    }
    
    /**
     * Validate username
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9_.-]{3,20}$");
    }
    
    /**
     * Validate password strength
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }
        // At least one uppercase, one lowercase, one digit, one special character
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
    
    /**
     * Validate product code
     */
    public static boolean isValidProductCode(String code) {
        if (code == null || code.trim().isEmpty()) {
            return false;
        }
        return code.matches("^[A-Z0-9]{4,15}$");
    }
    
    /**
     * Validate stock quantity
     */
    public static boolean isValidQuantity(int quantity) {
        return quantity >= 0;
    }
    
    /**
     * Validate price
     */
    public static boolean isValidPrice(double price) {
        return price > 0 && price < Double.MAX_VALUE;
    }
    
    /**
     * Validate barcode
     */
    public static boolean isValidBarcode(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return false;
        }
        return barcode.matches("^\\d{8,15}$");
    }
}
