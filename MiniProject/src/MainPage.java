import javax.swing.*;
import java.util.ArrayList;

public class MainPage extends JFrame {
    private static ArrayList<User> users;

    public MainPage() {
        setTitle("Main Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Add your main page components here
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            LoginPage loginPage = new LoginPage(users);
            loginPage.setVisible(true);
            this.setVisible(false);
        });

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> {
            RegisterPage registerPage = new RegisterPage(users);
            registerPage.register();
        });

        JPanel panel = new JPanel();
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
    }

    public static void main(String[] args) {
        users = RegisterPage.loadUsersFromFile();
        System.out.println("Loaded users: " + users);

        SwingUtilities.invokeLater(() -> {
            MainPage mainPage = new MainPage();
            mainPage.setVisible(true);
        });
    }
}
