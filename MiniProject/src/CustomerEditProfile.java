import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomerEditProfile extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel roleLabel;
    private JButton saveButton;
    private User currentCustomer;
    private List<User> customers;

    public CustomerEditProfile(JFrame parent, User currentCustomer, List<User> customers) {
        super(parent, "Edit Profile", true);
        this.currentCustomer = currentCustomer;
        this.customers = customers;

        setupUI();
    }

    private void setupUI() {
        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField(currentCustomer.getUsername());
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField(currentCustomer.getPassword());
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
        String newPassword = new String(passwordField.getPassword());

        if (!newUsername.isEmpty() && !newPassword.isEmpty()) {
            // Update current customer details
            currentCustomer.setUsername(newUsername);
            currentCustomer.setPassword(newPassword);

            // Find and update the customer in the list
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i).getUsername().equals(currentCustomer.getUsername())) {
                    customers.set(i, currentCustomer);
                    break;
                }
            }

            JOptionPane.showMessageDialog(this, "Profile updated successfully.", "Profile Update", JOptionPane.INFORMATION_MESSAGE);
            saveCustomersToFile();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cust.txt"))) {
            for (User customer : customers) {
                writer.write(customer.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
