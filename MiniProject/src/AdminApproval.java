import javax.swing.*;

public class AdminApproval {
    public boolean approveUserBooking(CustomerBooking booking) {
        int result = JOptionPane.showConfirmDialog(null, "Do you approve the booking for " + booking.getCar().getBrand() + " " + booking.getCar().getModel() + "?");
        return result == JOptionPane.YES_OPTION;
    }
}
