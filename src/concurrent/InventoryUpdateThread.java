package concurrent;

/**
 * InventoryUpdateThread: Thread for updating inventory with transaction retry logic
 */
public class InventoryUpdateThread extends Thread {
    private int productId;
    private int quantity;
    private String transactionType;
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 1000;
    
    public InventoryUpdateThread(int productId, int quantity, String transactionType) {
        this.productId = productId;
        this.quantity = quantity;
        this.transactionType = transactionType;
        setName("InventoryUpdate-" + productId);
    }
    
    @Override
    public void run() {
        int attempts = 0;
        boolean success = false;
        
        while (attempts < MAX_RETRIES && !success) {
            try {
                updateInventory();
                success = true;
                System.out.println("[INVENTORY] Updated product " + productId + 
                    " by " + quantity + " units (" + transactionType + ")");
            } catch (Exception e) {
                attempts++;
                System.err.println("[INVENTORY] Update failed (attempt " + attempts + "): " + e.getMessage());
                
                if (attempts < MAX_RETRIES) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS * attempts);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        
        if (!success) {
            System.err.println("[INVENTORY] Failed to update product " + productId + " after " + MAX_RETRIES + " attempts");
        }
    }
    
    /**
     * Perform the actual inventory update
     */
    private void updateInventory() throws Exception {
        // Simulate database update
        Thread.sleep(100);
    }
}
