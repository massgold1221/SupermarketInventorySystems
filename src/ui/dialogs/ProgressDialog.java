package ui.dialogs;

import javax.swing.*;

/**
 * ProgressDialog: Dialog for showing operation progress
 */
public class ProgressDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JProgressBar progressBar;
    private JLabel statusLabel;
    
    public ProgressDialog(JFrame parent, String title) {
        super(parent, title, true);
        
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        
        statusLabel = new JLabel("Processing...");
        
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(parent);
        
        add(statusLabel, "North");
        add(progressBar, "Center");
    }
    
    /**
     * Update progress
     */
    public void setProgress(int value) {
        progressBar.setValue(value);
    }
    
    /**
     * Set status message
     */
    public void setStatus(String message) {
        statusLabel.setText(message);
    }
}
