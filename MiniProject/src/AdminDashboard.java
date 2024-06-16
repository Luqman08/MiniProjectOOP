import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard {
    private AdminCarList carList;
    private List<CustomerBooking> bookings;
    private AdminApproval adminApproval; // Association with AdminApproval

    public AdminDashboard() {
        carList = new AdminCarList();
        bookings = new ArrayList<>();
        adminApproval = new AdminApproval(); // Initialize AdminApproval instance
    }

    public void viewDashboard() {
        carList.viewCarList();
        viewBookings();
    }

    public void viewBookings() {
        StringBuilder bookingInfo = new StringBuilder();
        for (CustomerBooking booking : bookings) {
            bookingInfo.append(booking).append("\n");
        }
        JOptionPane.showMessageDialog(null, bookingInfo.length() == 0 ? "No bookings available" : bookingInfo.toString());
    }

    public void showLoginPage() {
        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");
        if (!"admin".equals(username) || !"password".equals(password)) {
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password. Please try again.");
            showLoginPage();
        } else {
            JOptionPane.showMessageDialog(null, "Login successful!");
            viewDashboard();
        }
    }

    public void showLoginPage(Boolean un, Boolean pw) {
        if (!un || !pw) {
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password. Please try again.");
            showLoginPage();
        } else {
            JOptionPane.showMessageDialog(null, "Login successful!");
            viewDashboard();
        }
    }

    public void manageBooking() {
        for (CustomerBooking booking : bookings) {
            if ("Pending".equals(booking.getStatus())) {
                boolean approved = adminApproval.approveUserBooking(booking);
                booking.setStatus(approved ? "Accepted" : "Rejected");
                booking.getCar().setAvailable(approved); // Update car availability based on booking approval
                JOptionPane.showMessageDialog(null, "Booking for " + booking.getCar().getBrand() + " " + booking.getCar().getModel() + " has been " + (approved ? "accepted" : "rejected"));
            }
        }
    }

    public void manageCars() {
        carList.manageCars();
    }

    public static void main(String[] args) {
        AdminDashboard dashboard = new AdminDashboard();
        dashboard.showLoginPage();
    }
}
