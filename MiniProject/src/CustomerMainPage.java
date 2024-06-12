public class CustomerMainPage {
    private CustomerEditProfile customerEditProfile;
    private CustomerBooking customerBooking;

    public CustomerMainPage() {
        // Initialize customerBooking as it's always needed
        this.customerBooking = new CustomerBooking();
    }

    public void viewMainPage() {
        // Implement view main page logic here
    }

    public void searchCar() {
        // Implement search car logic here
    }

    public void about() {
        // Implement about logic here
    }

    public boolean bookLogin(CustomerBooking booking) {
        // Implement book login logic here
        return false;
    }

    public boolean bookRegister(CustomerBooking booking) {
        // Implement book register logic here
        return false;
    }

    public void manageBooking(CustomerBooking booking) {
        // Implement manage booking logic here
    }

    public void editProfile() {
        if (this.customerEditProfile == null) {
            this.customerEditProfile = new CustomerEditProfile();
        }
        // Implement edit profile logic using this.customerEditProfile
    }

    // Optional method to check if edit profile is available
    public boolean isEditProfileAvailable() {
        return this.customerEditProfile != null;
    }

    public static void main(String[] args) {
        CustomerMainPage mainPage = new CustomerMainPage();
        // Test main page functionalities
    }
}
