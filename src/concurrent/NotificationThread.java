package concurrent;

/**
 * NotificationThread: Thread for handling alert notifications
 */
public class NotificationThread extends Thread {
    private String notification;
    private long interval;
    private volatile boolean running;
    
    public NotificationThread(String notification, long intervalMs) {
        this.notification = notification;
        this.interval = intervalMs;
        this.running = true;
        setName("Notification-" + System.currentTimeMillis());
    }
    
    @Override
    public void run() {
        try {
            System.out.println("[NOTIFICATION] Started notification thread");
            
            while (running) {
                try {
                    System.out.println("[ALERT] " + notification);
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        } finally {
            System.out.println("[NOTIFICATION] Notification thread stopped");
        }
    }
    
    /**
     * Stop the notification
     */
    public void stopNotification() {
        running = false;
        interrupt();
    }
}
