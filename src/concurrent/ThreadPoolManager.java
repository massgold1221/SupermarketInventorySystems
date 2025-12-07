package concurrent;

import java.util.concurrent.*;

/**
 * ThreadPoolManager: Manages thread pools for concurrent operations
 * Enhanced with monitoring capabilities
 */
public class ThreadPoolManager {
    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutor;
    private static final int CORE_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private long tasksSubmitted;
    private long tasksCompleted;
    
    public ThreadPoolManager() {
        this.executorService = new ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
        );
        this.scheduledExecutor = Executors.newScheduledThreadPool(5);
        this.tasksSubmitted = 0;
        this.tasksCompleted = 0;
    }
    
    /**
     * Submit a task for execution
     */
    public Future<?> submitTask(Runnable task) {
        tasksSubmitted++;
        return executorService.submit(task);
    }
    
    /**
     * Submit a scheduled task
     */
    public ScheduledFuture<?> scheduleTask(Runnable task, long delay, TimeUnit unit) {
        tasksSubmitted++;
        return scheduledExecutor.schedule(task, delay, unit);
    }
    
    /**
     * Submit a repeating task
     */
    public ScheduledFuture<?> scheduleRepeatingTask(Runnable task, long initialDelay, long period, TimeUnit unit) {
        tasksSubmitted++;
        return scheduledExecutor.scheduleAtFixedRate(task, initialDelay, period, unit);
    }
    
    /**
     * Get thread pool statistics
     */
    public String getPoolStats() {
        if (executorService instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor tpe = (ThreadPoolExecutor) executorService;
            return String.format("Core: %d, Max: %d, Active: %d, Queued: %d, Completed: %d",
                tpe.getCorePoolSize(), tpe.getMaximumPoolSize(), tpe.getActiveCount(),
                tpe.getQueue().size(), tpe.getCompletedTaskCount());
        }
        return "Pool stats unavailable";
    }
    
    /**
     * Shutdown all executors
     */
    public void shutdown() {
        System.out.println("[POOL] Shutting down thread pools...");
        
        executorService.shutdown();
        scheduledExecutor.shutdown();
        
        try {
            if (!executorService.awaitTermination(30, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
            if (!scheduledExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
                scheduledExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            scheduledExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        
        System.out.println("[POOL] Thread pools shutdown complete");
    }
}
