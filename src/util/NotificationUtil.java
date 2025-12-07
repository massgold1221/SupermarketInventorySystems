package util;

/**
 * NotificationUtil: Alert notification utilities
 */
public class NotificationUtil {
    
    /**
     * Send email notification
     */
    public static boolean sendEmailNotification(String recipient, String subject, String message) {
        System.out.println("[NOTIFICATION] Sending email to: " + recipient);
        System.out.println("[NOTIFICATION] Subject: " + subject);
        System.out.println("[NOTIFICATION] Message: " + message);
        // Implement SMTP integration here
        return true;
    }
    
    /**
     * Send SMS notification
     */
    public static boolean sendSmsNotification(String phoneNumber, String message) {
        System.out.println("[NOTIFICATION] Sending SMS to: " + phoneNumber);
        System.out.println("[NOTIFICATION] Message: " + message);
        // Implement SMS gateway integration here
        return true;
    }
    
    /**
     * Send in-app notification
     */
    public static boolean sendInAppNotification(int userId, String title, String message) {
        System.out.println("[NOTIFICATION] Sending in-app notification to user: " + userId);
        System.out.println("[NOTIFICATION] Title: " + title);
        System.out.println("[NOTIFICATION] Message: " + message);
        return true;
    }
    
    /**
     * Send low stock alert
     */
    public static boolean sendLowStockAlert(String productName, int currentStock, int minStock) {
        String message = "ALERT: " + productName + " stock level (" + currentStock + ") is below minimum (" + minStock + ")";
        Logger.warn(message);
        // Notify admin/managers
        return sendInAppNotification(1, "Low Stock Alert", message);
    }
    
    /**
     * Send critical alert
     */
    public static boolean sendCriticalAlert(String message) {
        Logger.fatal("CRITICAL: " + message);
        // Notify all admins immediately
        return true;
    }
}
