package concurrent;

/**
 * ReportGenerationThread: Thread for generating reports with progress tracking
 */
public class ReportGenerationThread extends Thread {
    private String reportType;
    private String outputPath;
    private volatile int progress;
    private volatile boolean cancelled;
    
    public ReportGenerationThread(String reportType, String outputPath) {
        this.reportType = reportType;
        this.outputPath = outputPath;
        this.progress = 0;
        this.cancelled = false;
        setName("ReportGen-" + reportType);
    }
    
    @Override
    public void run() {
        try {
            System.out.println("[REPORT] Starting generation of " + reportType + " report");
            
            for (int i = 0; i < 10; i++) {
                if (cancelled) {
                    System.out.println("[REPORT] Report generation cancelled");
                    return;
                }
                
                // Simulate report generation steps
                Thread.sleep(1000);
                progress = (i + 1) * 10;
                System.out.println("[REPORT] Progress: " + progress + "%");
            }
            
            System.out.println("[REPORT] Report generated successfully: " + outputPath);
        } catch (InterruptedException e) {
            System.err.println("[REPORT] Report generation interrupted");
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Get current progress
     */
    public int getProgress() {
        return progress;
    }
    
    /**
     * Cancel report generation
     */
    public void cancelGeneration() {
        cancelled = true;
    }
}
