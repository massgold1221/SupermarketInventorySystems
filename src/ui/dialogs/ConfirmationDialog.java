package ui.dialogs;

import javax.swing.*;

/**
 * ConfirmationDialog: Reusable confirmation dialog
 */
public class ConfirmationDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private int result;
    
    public ConfirmationDialog(JFrame parent, String title, String message) {
        super(parent, title, true);
        
        JLabel messageLabel = new JLabel(message);
        
        JButton yesButton = new JButton("Yes");
        JButton noButton = new JButton("No");
        JButton cancelButton = new JButton("Cancel");
        
        yesButton.addActionListener(e -> {
            result = JOptionPane.YES_OPTION;
            dispose();
        });
        
        noButton.addActionListener(e -> {
            result = JOptionPane.NO_OPTION;
            dispose();
        });
        
        cancelButton.addActionListener(e -> {
            result = JOptionPane.CANCEL_OPTION;
            dispose();
        });
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(parent);
        
        // Add components to dialog
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        buttonPanel.add(cancelButton);
        
        add(messageLabel, "Center");
        add(buttonPanel, "South");
        
        result = JOptionPane.CANCEL_OPTION;
    }
    
    /**
     * Get dialog result
     */
    public int getResult() {
        return result;
    }
}
