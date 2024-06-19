import javax.swing.JOptionPane;
import java.io.Serializable;

public class CustomerEditProfile implements Serializable {
    private String name;
    private String email;
    private String phone;

    public CustomerEditProfile(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void updateProfile() {
        System.out.println("Profile updated: " + this);
        JOptionPane.showMessageDialog(null, "Profile updated successfully.", "Profile Update",
                JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public String toString() {
        return "CustomerEditProfile{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public void showProfileInfoDialog() {
        String message = "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone;

        JOptionPane.showMessageDialog(null, message, "Profile Details", JOptionPane.INFORMATION_MESSAGE);
    }
}
