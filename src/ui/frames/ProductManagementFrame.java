package ui.frames;

import javax.swing.*;

/**
 * ProductManagementFrame: Product management interface (Placeholder)
 */
public class ProductManagementFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTable productsTable;
    private JButton addButton, updateButton, deleteButton, searchButton;
    
    public ProductManagementFrame() {
        setTitle("Product Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Initialize components
        productsTable = new JTable();
        addButton = new JButton("Add Product");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        searchButton = new JButton("Search");
        
        // Add layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        
        add(new JScrollPane(productsTable), "Center");
        add(buttonPanel, "South");
    }
}
