package ui.frames;

import javax.swing.*;

/**
 * LoginFrame: User authentication frame (Placeholder)
 */
public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    public LoginFrame() {
        setTitle("Supermarket Inventory System - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        
        // Initialize components
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        
        // Layout components
        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        
        add(panel);
    }
}
