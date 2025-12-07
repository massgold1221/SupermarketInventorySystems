package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * SecurityUtil: Password hashing and encryption utilities
 */
public class SecurityUtil {
    
    /**
     * Hash a password using SHA-256
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            Logger.error("Error hashing password", e);
            return null;
        }
    }
    
    /**
     * Verify password against hash
     */
    public static boolean verifyPassword(String password, String hash) {
        String hashOfInput = hashPassword(password);
        return hashOfInput != null && hashOfInput.equals(hash);
    }
    
    /**
     * Generate a random token
     */
    public static String generateToken() {
        byte[] tokenBytes = new byte[32];
        java.util.Random random = new java.util.Random();
        random.nextBytes(tokenBytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
    }
    
    /**
     * Encrypt data using simple XOR (NOT FOR PRODUCTION)
     */
    public static String encryptData(String data, String key) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            result.append((char) (data.charAt(i) ^ key.charAt(i % key.length())));
        }
        return Base64.getEncoder().encodeToString(result.toString().getBytes());
    }
    
    /**
     * Decrypt data using simple XOR (NOT FOR PRODUCTION)
     */
    public static String decryptData(String encryptedData, String key) {
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
        String data = new String(decodedBytes);
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            result.append((char) (data.charAt(i) ^ key.charAt(i % key.length())));
        }
        return result.toString();
    }
}
