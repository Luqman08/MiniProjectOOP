import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class RegisterPage {
    private ArrayList<User> users;

    public RegisterPage(ArrayList<User> users) {
        this.users = users;
    }

    public void register() {
        String username = JOptionPane.showInputDialog(null, "Enter Username:");
        String password = JOptionPane.showInputDialog(null, "Enter Password:");
        String role = "customer"; // Set the role to "customer"

        if (username != null && password != null) {
            users.add(new User(username, password, role));
            saveUsersToFile();
            JOptionPane.showMessageDialog(null, "Registration Successful!");
        } else {
            JOptionPane.showMessageDialog(null, "Registration Failed! Please fill all fields.");
        }
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cust.txt"))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> loadUsersFromFile() {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("cust.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(User.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
}
