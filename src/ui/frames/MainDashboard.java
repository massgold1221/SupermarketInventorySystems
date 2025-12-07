package ui.frames;

import javax.swing.*;

/**
 * MainDashboard: Main application dashboard (Placeholder)
 */
public class MainDashboard extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JMenuBar menuBar;
    private JMenu fileMenu, inventoryMenu, reportMenu, settingsMenu;
    
    public MainDashboard() {
        setTitle("Supermarket Inventory System - Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        
        // Create menu bar
        menuBar = new JMenuBar();
        
        // File menu
        fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem("Exit"));
        
        // Inventory menu
        inventoryMenu = new JMenu("Inventory");
        inventoryMenu.add(new JMenuItem("Products"));
        inventoryMenu.add(new JMenuItem("Categories"));
        inventoryMenu.add(new JMenuItem("Suppliers"));
        
        // Report menu
        reportMenu = new JMenu("Reports");
        reportMenu.add(new JMenuItem("Inventory Report"));
        reportMenu.add(new JMenuItem("Sales Report"));
        
        // Settings menu
        settingsMenu = new JMenu("Settings");
        settingsMenu.add(new JMenuItem("Preferences"));
        settingsMenu.add(new JMenuItem("Users"));
        
        menuBar.add(fileMenu);
        menuBar.add(inventoryMenu);
        menuBar.add(reportMenu);
        menuBar.add(settingsMenu);
        
        setJMenuBar(menuBar);
    }
}
