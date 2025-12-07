package ui.frames;

import javax.swing.*;

/**
 * SupplierFrame: Supplier management interface (Placeholder)
 */
public class SupplierFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTable suppliersTable;
    private JButton addButton, updateButton, deleteButton;
    
    public SupplierFrame() {
        setTitle("Supplier Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        suppliersTable = new JTable();
        addButton = new JButton("Add Supplier");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        
        add(new JScrollPane(suppliersTable), "Center");
        add(buttonPanel, "South");
    }
}
