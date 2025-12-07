package concurrent;

/**
 * TaskScheduler: Scheduled task management
 */
public class TaskScheduler {
    private ThreadPoolManager threadPoolManager;
    
    public TaskScheduler() {
        this.threadPoolManager = new ThreadPoolManager();
    }
    
    /**
     * Schedule inventory check
     */
    public void scheduleInventoryCheck(long intervalMinutes) {
        threadPoolManager.scheduleRepeatingTask(
            this::checkInventory,
            1, intervalMinutes,
            java.util.concurrent.TimeUnit.MINUTES
        );
        System.out.println("[SCHEDULER] Inventory check scheduled every " + intervalMinutes + " minutes");
    }
    
    /**
     * Schedule report generation
     */
    public void scheduleReportGeneration(long intervalHours) {
        threadPoolManager.scheduleRepeatingTask(
            this::generateDailyReport,
            1, intervalHours,
            java.util.concurrent.TimeUnit.HOURS
        );
        System.out.println("[SCHEDULER] Report generation scheduled every " + intervalHours + " hours");
    }
    
    /**
     * Schedule alert notifications
     */
    public void scheduleAlertNotifications(long intervalSeconds) {
        threadPoolManager.scheduleRepeatingTask(
            this::checkAndNotifyAlerts,
            5, intervalSeconds,
            java.util.concurrent.TimeUnit.SECONDS
        );
        System.out.println("[SCHEDULER] Alert notifications scheduled every " + intervalSeconds + " seconds");
    }
    
    private void checkInventory() {
        System.out.println("[TASK] Running scheduled inventory check...");
    }
    
    private void generateDailyReport() {
        System.out.println("[TASK] Generating daily report...");
    }
    
    private void checkAndNotifyAlerts() {
        System.out.println("[TASK] Checking alerts...");
    }
    
    /**
     * Shutdown scheduler
     */
    public void shutdown() {
        threadPoolManager.shutdown();
    }
}
