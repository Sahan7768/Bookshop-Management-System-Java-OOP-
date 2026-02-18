import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LoginSystem extends JFrame {
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 73, 94);
    private static final Color ACCENT_COLOR = new Color(26, 188, 156);
    private static final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private static final Color LIGHT_BG = new Color(236, 240, 241);
    private static final Color WHITE = Color.WHITE;
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private List<User> users;
    
    public LoginSystem() {
        users = FileManager.loadUsers();
        
        setTitle("City Bookshop - Login");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
        
        setVisible(true);
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(LIGHT_BG);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 150));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        JLabel titleLabel = new JLabel("📚 City Bookshop");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Management System");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        subtitleLabel.setForeground(WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(Box.createVerticalGlue());
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(subtitleLabel);
        headerPanel.add(Box.createVerticalGlue());
        
        // Login Panel
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(WHITE);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Login title
        JLabel loginTitle = new JLabel("Login to Your Account");
        loginTitle.setFont(new Font("Arial", Font.BOLD, 20));
        loginTitle.setForeground(SECONDARY_COLOR);
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Username panel
        JPanel usernamePanel = new JPanel(new BorderLayout(10, 5));
        usernamePanel.setOpaque(false);
        usernamePanel.setMaximumSize(new Dimension(400, 70));
        
        JLabel usernameLabel = new JLabel("👤 Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setForeground(SECONDARY_COLOR);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        usernameField.setPreferredSize(new Dimension(0, 40));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        usernamePanel.add(usernameLabel, BorderLayout.NORTH);
        usernamePanel.add(usernameField, BorderLayout.CENTER);
        
        // Password panel
        JPanel passwordPanel = new JPanel(new BorderLayout(10, 5));
        passwordPanel.setOpaque(false);
        passwordPanel.setMaximumSize(new Dimension(400, 70));
        
        JLabel passwordLabel = new JLabel("🔒 Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setForeground(SECONDARY_COLOR);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setPreferredSize(new Dimension(0, 40));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);
        passwordPanel.add(passwordField, BorderLayout.CENTER);
        
        // Login button
        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 16));
        loginButton.setBackground(SUCCESS_COLOR);
        loginButton.setForeground(WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(0, 45));
        loginButton.setMaximumSize(new Dimension(400, 45));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        loginButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(SUCCESS_COLOR.darker());
            }
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(SUCCESS_COLOR);
            }
        });
        
        loginButton.addActionListener(e -> login());
        
        // Enter key support
        passwordField.addActionListener(e -> login());
        
        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        infoPanel.setMaximumSize(new Dimension(400, 150));
        
        JLabel infoTitle = new JLabel("Default Login Credentials:");
        infoTitle.setFont(new Font("Arial", Font.BOLD, 13));
        infoTitle.setForeground(SECONDARY_COLOR);
        infoTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel managerInfo = new JLabel("Manager: admin / admin123");
        managerInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        managerInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel cashierInfo = new JLabel("Cashier: cashier / cash123");
        cashierInfo.setFont(new Font("Arial", Font.PLAIN, 12));
        cashierInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        infoPanel.add(infoTitle);
        infoPanel.add(Box.createVerticalStrut(8));
        infoPanel.add(managerInfo);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(cashierInfo);
        
        // Add components to login panel
        loginPanel.add(loginTitle);
        loginPanel.add(Box.createVerticalStrut(30));
        loginPanel.add(usernamePanel);
        loginPanel.add(Box.createVerticalStrut(15));
        loginPanel.add(passwordPanel);
        loginPanel.add(Box.createVerticalStrut(25));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createVerticalStrut(25));
        loginPanel.add(infoPanel);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        
        add(mainPanel);
    }
    
    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please enter username and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                // Login successful
                dispose();
                new BookshopSystem(user);
                return;
            }
        }
        
        showMessage("Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        passwordField.setText("");
    }
    
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new LoginSystem());
    }
}
