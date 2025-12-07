package ui.frames;

import javax.swing.*;

/**
 * InventoryFrame: Inventory management interface (Placeholder)
 */
public class InventoryFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTable inventoryTable;
    private JLabel statusLabel;
    
    public InventoryFrame() {
        setTitle("Inventory Status");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        inventoryTable = new JTable();
        statusLabel = new JLabel("Real-time inventory status");
        
        add(new JScrollPane(inventoryTable), "Center");
        add(statusLabel, "South");
    }
}
