import javax.swing.*;
import java.util.ArrayList;

public class LoginPage {
    private ArrayList<User> users;

    public LoginPage(ArrayList<User> users) {
        this.users = users;
    }

    public void login() {
        String username = JOptionPane.showInputDialog(null, "Enter Username:");
        String password = JOptionPane.showInputDialog(null, "Enter Password:");
        boolean isAuthenticated = authenticate(username, password);
        if (isAuthenticated) {
            JOptionPane.showMessageDialog(null, "Login Successful!");
            MainPage mainPage = new MainPage();
            mainPage.display();
        } else {
            JOptionPane.showMessageDialog(null, "Login Failed. Please try again.");
        }
    }

    private boolean authenticate(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
