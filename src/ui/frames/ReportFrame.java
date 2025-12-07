package ui.frames;

import javax.swing.*;

/**
 * ReportFrame: Report generation interface (Placeholder)
 */
public class ReportFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JComboBox<String> reportTypeCombo;
    private JButton generateButton, exportButton;
    private JTextArea reportArea;
    
    public ReportFrame() {
        setTitle("Reports");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        reportTypeCombo = new JComboBox<>(new String[]{"Inventory Summary", "Sales Report", "Stock Movement"});
        generateButton = new JButton("Generate");
        exportButton = new JButton("Export");
        reportArea = new JTextArea();
        
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Report Type:"));
        controlPanel.add(reportTypeCombo);
        controlPanel.add(generateButton);
        controlPanel.add(exportButton);
        
        add(controlPanel, "North");
        add(new JScrollPane(reportArea), "Center");
    }
}
