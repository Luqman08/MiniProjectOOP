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
        users.add(new User(username, password));
        saveUsersToFile();
        JOptionPane.showMessageDialog(null, "Registration Successful!");
    }

    private void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
