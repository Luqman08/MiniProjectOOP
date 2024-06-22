import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class LoginPage extends JFrame implements ActionListener {
    // UI components
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private ArrayList<User> users;

    public LoginPage(ArrayList<User> users) {
        this.users = users;
        // UI setup code
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout and components
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        add(loginButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        System.out.println("Trying to authenticate user: " + username + " with password: " + password); // Debug statement

        User user = authenticate(username, password);
        if (user != null) {
            String role = user.getRole();
            System.out.println("Authenticated user: " + username + " with role: " + role); // Debug statement

            if ("admin".equals(role)) {
                JOptionPane.showMessageDialog(this, "Welcome, Admin!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                AdminDashboard adminDashboard = new AdminDashboard();
                adminDashboard.display();
            } else {
                JOptionPane.showMessageDialog(this, "Welcome, Customer!", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                CustomerMainPage customerMainPage = new CustomerMainPage(user);
                customerMainPage.display();
            }

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private User authenticate(String username, String password) {
        for (User user : users) {
            System.out.println("Checking against user: " + user.getUsername() + " with password: " + user.getPassword()); // Debug statement
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }
}
