import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminDashboard extends JFrame {
    private AdminCarList carList;
    private List<CustomerBooking> bookings;
    private AdminApproval adminApproval;

    public AdminDashboard() {
        carList = new AdminCarList();
        bookings = new ArrayList<>();
        adminApproval = new AdminApproval();
        loadBookings();
        setupUI();
    }

    private void setupUI() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton viewCarListButton = new JButton("View Car List");
        viewCarListButton.addActionListener(e -> carList.viewCarList());

        JButton viewBookingsButton = new JButton("View Bookings");
        viewBookingsButton.addActionListener(e -> viewBookings());

        JButton manageCarsButton = new JButton("Manage Cars");
        manageCarsButton.addActionListener(e -> carList.manageCars());

        JButton manageBookingsButton = new JButton("Manage Bookings");
        manageBookingsButton.addActionListener(e -> manageBooking());

        JPanel panel = new JPanel();
        panel.add(viewCarListButton);
        panel.add(viewBookingsButton);
        panel.add(manageCarsButton);
        panel.add(manageBookingsButton);

        add(panel);
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void viewBookings() {
        StringBuilder bookingInfo = new StringBuilder();
        for (CustomerBooking booking : bookings) {
            bookingInfo.append(booking).append("\n");
        }
        JOptionPane.showMessageDialog(null, bookingInfo.length() == 0 ? "No bookings available" : bookingInfo.toString());
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
        saveBookings();
    }

    private void loadBookings() {
        try (BufferedReader reader = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                Car car = new Car(parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5]));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date bookingDate = dateFormat.parse(parts[6]);
                Date returnDate = dateFormat.parse(parts[7]);
                CustomerBooking booking = new CustomerBooking(parts[0], car, bookingDate, returnDate, parts[8]);
                booking.setStatus(parts[9]);
                bookings.add(booking);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void saveBookings() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("bookings.txt"))) {
            for (CustomerBooking booking : bookings) {
                writer.write(String.join(", ",
                        booking.getBookingId(),
                        booking.getCar().getCarId(),
                        booking.getCar().getBrand(),
                        booking.getCar().getModel(),
                        booking.getCar().getColour(),
                        String.valueOf(booking.getCar().isAvailable()),
                        new SimpleDateFormat("yyyy-MM-dd").format(booking.getBookingDate()),
                        new SimpleDateFormat("yyyy-MM-dd").format(booking.getReturnDate()),
                        booking.getCustomerUsername(),
                        booking.getStatus()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AdminDashboard adminDashboard = new AdminDashboard();
        adminDashboard.showLoginPage();
    }

    public void showLoginPage() {
        String username = JOptionPane.showInputDialog("Enter Username:");
        String password = JOptionPane.showInputDialog("Enter Password:");
        if (!"admin".equals(username) || !"password".equals(password)) {
            JOptionPane.showMessageDialog(null, "Incorrect Username or Password. Please try again.");
            showLoginPage();
        } else {
            JOptionPane.showMessageDialog(null, "Login successful!");
            display();
        }
    }
}
