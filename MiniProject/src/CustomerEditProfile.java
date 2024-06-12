public class CustomerEditProfile {
    private String name;
    private String email;
    private String phone;

    public void editProfile(String name, String email, String phone) {
        updateName(name);
        updateEmail(email);
        updatePhone(phone);
        // Implement additional edit profile logic here
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    // Optional method to display the current profile
    public String displayProfile() {
        return "Name: " + this.name + ", Email: " + this.email + ", Phone: " + this.phone;
    }
}
