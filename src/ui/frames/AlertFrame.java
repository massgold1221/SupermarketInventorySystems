package ui.frames;

import javax.swing.*;

/**
 * AlertFrame: Stock alerts management interface (Placeholder)
 */
public class AlertFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTable alertsTable;
    private JButton acknowledgeButton, settingsButton;
    
    public AlertFrame() {
        setTitle("Stock Alerts");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        alertsTable = new JTable();
        acknowledgeButton = new JButton("Acknowledge");
        settingsButton = new JButton("Settings");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(acknowledgeButton);
        buttonPanel.add(settingsButton);
        
        add(new JScrollPane(alertsTable), "Center");
        add(buttonPanel, "South");
    }
}
