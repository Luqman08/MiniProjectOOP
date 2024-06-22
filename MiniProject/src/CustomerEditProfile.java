import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerEditProfile extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField; // Changed to JPasswordField for security
    private JLabel roleLabel;
    private JButton saveButton;
    private User currentCustomer;

    public CustomerEditProfile(JFrame parent, User currentCustomer) {
        super(parent, "Edit Profile", true);
        this.currentCustomer = currentCustomer;
        
        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(4, 2));
        
        add(new JLabel("Username:"));
        usernameField = new JTextField(currentCustomer.getUsername());
        add(usernameField);
        
        add(new JLabel("Password:"));
        passwordField = new JPasswordField(currentCustomer.getPassword()); // Changed to JPasswordField
        add(passwordField);
        
        add(new JLabel("Role:"));
        roleLabel = new JLabel(currentCustomer.getRole());
        add(roleLabel);
        
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { 
                saveProfile();
            }
        });
        add(saveButton);
        
        setSize(300, 200);
        setLocationRelativeTo(getParent());
    }

    private void saveProfile() {
        String newUsername = usernameField.getText();
        String newPassword = new String(passwordField.getPassword()); // Convert password to String
        
        if (!newUsername.isEmpty() && !newPassword.isEmpty()) {
            currentCustomer.setUsername(newUsername);
            currentCustomer.setPassword(newPassword);
            JOptionPane.showMessageDialog(this, "Profile updated successfully.", "Profile Update", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
