package ui.frames;

import javax.swing.*;

/**
 * TransactionFrame: Transaction management interface (Placeholder)
 */
public class TransactionFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTable transactionsTable;
    private JButton recordButton, viewButton, printButton;
    
    public TransactionFrame() {
        setTitle("Inventory Transactions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        
        transactionsTable = new JTable();
        recordButton = new JButton("Record Transaction");
        viewButton = new JButton("View Details");
        printButton = new JButton("Print Receipt");
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(recordButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(printButton);
        
        add(new JScrollPane(transactionsTable), "Center");
        add(buttonPanel, "South");
    }
}
