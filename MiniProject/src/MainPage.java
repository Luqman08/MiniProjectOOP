import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class MainPage {
    public static void main(String[] args) {
        ArrayList<User> users = loadUsersFromTextFile();
        String[] options = { "Login", "Register" };
        int choice = JOptionPane.showOptionDialog(null, "Choose an option", "User Authentication",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            LoginPage loginPage = new LoginPage(users);
            loginPage.login();
        } else if (choice == 1) {
            RegisterPage registerPage = new RegisterPage(users);
            registerPage.register();
        }
    }

    private static ArrayList<User> loadUsersFromTextFile() {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    users.add(new User(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void display() {
        JOptionPane.showMessageDialog(null, "Welcome to the Car Rental Website!");
    }
}
