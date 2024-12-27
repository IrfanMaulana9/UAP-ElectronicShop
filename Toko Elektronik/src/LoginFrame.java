import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LoginFrame extends JFrame {
    public ArrayList<User> users;
    public JTextField usernameField;
    public JPasswordField passwordField;

    public LoginFrame() {
        initializeUsers();
        initializeUI();
    }

    public void initializeUsers() {
        users = new ArrayList<>();
        // Menambahkan default admin
        users.add(new User("admin", "admin123", "ADMIN"));
        // Menambahkan default customer
        users.add(new User("customer", "customer123", "CUSTOMER"));
    }

    public void initializeUI() {
        setTitle("Login Toko Elektronik");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Username
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(15);
        panel.add(usernameField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        panel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        panel.add(loginButton, gbc);

        add(panel);
    }

    public void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        User user = users.stream()
                .filter(u -> u.getUsername().equals(username) &&
                        u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user != null) {
            dispose();
            if (user.getRole().equals("ADMIN")) {
                new AdminFrame().setVisible(true);
            } else {
                new CustomerFrame().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Username atau password salah!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}