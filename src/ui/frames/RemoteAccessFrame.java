package ui.frames;

import javax.swing.*;

/**
 * RemoteAccessFrame: Remote client access interface (Placeholder)
 */
public class RemoteAccessFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JLabel connectionStatusLabel;
    private JButton connectButton, disconnectButton;
    private JTextArea logArea;
    
    public RemoteAccessFrame() {
        setTitle("Remote Access");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        
        connectionStatusLabel = new JLabel("Status: Disconnected");
        connectButton = new JButton("Connect");
        disconnectButton = new JButton("Disconnect");
        logArea = new JTextArea();
        
        JPanel controlPanel = new JPanel();
        controlPanel.add(connectionStatusLabel);
        controlPanel.add(connectButton);
        controlPanel.add(disconnectButton);
        
        add(controlPanel, "North");
        add(new JScrollPane(logArea), "Center");
    }
}
