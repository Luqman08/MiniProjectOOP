import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String role;

    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public static User fromString(String str) {
        String[] parts = str.split(",");
        if (parts.length == 3) {
            return new User(parts[0].trim(), parts[1].trim(), parts[2].trim());
        } else {
            throw new IllegalArgumentException("Invalid user data: " + str);
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return username + "," + password + "," + role;
    }
}
