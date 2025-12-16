package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;

    public LoginView(String xmlPath) {
        setTitle("Login - Policija");
        ImageIcon icon = new ImageIcon("resources/policija.jpg");
        setIconImage(icon.getImage());
        setSize(800, 600); // Adjusted size
        setResizable(false); // Disable resizing
        setUndecorated(true); // Remove window decorations
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create a JLayeredPane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(800, 600));
        getContentPane().add(layeredPane, BorderLayout.CENTER);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(getClass().getResource("/policija.jpg"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(800, 600, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        backgroundLabel.setBounds(0, 0, 800, 600); // Full screen
        layeredPane.add(backgroundLabel, JLayeredPane.DEFAULT_LAYER);
         
        // Form Panel   
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(new Color(0f,0f,0f,0.5f));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 2, 2, 2); // Further reduced spacing
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        // Username Label
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Black color
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Bold and larger font
        usernameLabel.setOpaque(true); // Enable opacity
        usernameLabel.setBackground(new Color(0f, 0f, 0f, 0.5f)); 
        // Username Field
        usernameField = new JTextField(22); // Increased width
        usernameField.setPreferredSize(new Dimension(80, 22)); // Increased height
        usernameField.setForeground(new Color(240, 176, 2)); // Orange background
        usernameField.setOpaque(true);
        usernameField.setBackground(new Color(0,0,0,220)); 
        // Password Label
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE); // Black color
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 16)); // Bold and larger font
        passwordLabel.setOpaque(true); // Enable opacity
        passwordLabel.setBackground(new Color(0f, 0f, 0f, 0.5f)); 
        // Password Field
        passwordField = new JPasswordField(22); // Increased width
        passwordField.setPreferredSize(new Dimension(80, 22)); // Increased height
        passwordField.setForeground(new Color(240, 176, 2)); // Orange background
        usernameField.setOpaque(true);
        passwordField.setBackground(new Color(0,0,0,220)); 

        gbc.gridy++;
        formPanel.add(usernameLabel, gbc);
        gbc.gridy++;
        formPanel.add(usernameField, gbc);
        gbc.gridy++;
        formPanel.add(passwordLabel, gbc);
        gbc.gridy++;
        formPanel.add(passwordField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setBackground(new Color(0f,0f,0f,.5f ));
        loginButton = new JButton("Prihvati");
        loginButton.setBackground(new Color(240, 176, 2));
        loginButton.setForeground(Color.BLACK);
        cancelButton = new JButton("Odustani");
        cancelButton.setBackground(new Color(240, 176, 2)); // Same color as input fields with 70% opacity

        cancelButton.setForeground(Color.BLACK);
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);

        gbc.gridy++;
        formPanel.add(buttonPanel, gbc);

        // Form Panel positioning
        formPanel.setOpaque(false); // Make sure it's transparent to show background
        formPanel.setBounds(150, 200, 500, 300); // Centered position with adjusted size
        layeredPane.add(formPanel, JLayeredPane.PALETTE_LAYER);

        // Set window position
        setLocationRelativeTo(null); // Ensure the window is centered on the screen
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void addCancelListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}
