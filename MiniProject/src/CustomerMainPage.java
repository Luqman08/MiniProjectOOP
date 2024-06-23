import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerMainPage extends JFrame {
    private List<Car> availableCars;
    private List<CustomerBooking> bookings;
    private List<User> customers;
    private User currentCustomer;

    public CustomerMainPage(User currentCustomer) {
        this.currentCustomer = currentCustomer;
        availableCars = new ArrayList<>();
        bookings = new ArrayList<>();
        customers = new ArrayList<>();
        loadCars();
        loadCustomers();
        loadBookings();

        setupUI();
    }

    private void setupUI() {
        setTitle("Customer Main Page");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton viewCarsButton = new JButton("View Available Cars");
        viewCarsButton.addActionListener(e -> viewAvailableCars());

        JButton bookCarButton = new JButton("Book a Car");
        bookCarButton.addActionListener(e -> bookCarDialog());

        JButton viewBookingsButton = new JButton("View Booking History");
        viewBookingsButton.addActionListener(e -> viewBookingHistory());

        JButton editProfileButton = new JButton("Edit Profile");
        editProfileButton.addActionListener(e -> editProfile());

        JPanel panel = new JPanel();
        panel.add(viewCarsButton);
        panel.add(bookCarButton);
        panel.add(viewBookingsButton);
        panel.add(editProfileButton);

        add(panel);
    }

    public void display() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public void viewAvailableCars() {
        StringBuilder carList = new StringBuilder();
        carList.append("Available Cars:\n");
        carList.append(String.format("%-10s %-15s %-15s %-10s\n", "Car ID", "Brand", "Model", "Colour"));
        carList.append("--------------------------------------------------------------\n");
        for (Car car : availableCars) {
            if (car.isAvailable()) {
                carList.append(String.format("%-10s %-15s %-15s %-10s\n", car.getCarId(), car.getBrand(), car.getModel(), car.getColour()));
            }
        }
        JOptionPane.showMessageDialog(null, carList.length() == 0 ? "No cars available." : carList.toString());
    }

    public void makeBooking(CustomerBooking booking) {
        bookings.add(booking);
        booking.makeBooking();
        saveBookings();
    }

    private void bookCarDialog() {
        String carId = JOptionPane.showInputDialog(null, "Enter Car ID:");
        String pickupDateStr = JOptionPane.showInputDialog(null, "Enter Pickup Date (yyyy-MM-dd):");
        String returnDateStr = JOptionPane.showInputDialog(null, "Enter Return Date (yyyy-MM-dd):");

        try {
            Date pickupDate = new SimpleDateFormat("yyyy-MM-dd").parse(pickupDateStr);
            Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDateStr);
            bookCar(carId, pickupDate, returnDate);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date in yyyy-MM-dd format.");
        }
    }

    private void editProfile() {
        CustomerEditProfile dialog = new CustomerEditProfile(this, currentCustomer, customers);
        dialog.setVisible(true);
        saveCustomers();
    }

    public void loadCars() {
        try (BufferedReader reader = new BufferedReader(new FileReader("car.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 5) {
                    Car car = new Car(parts[0], parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
                    availableCars.add(car);
                } else {
                    System.err.println("Invalid car data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCustomers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cust.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                customers.add(User.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cust.txt"))) {
            for (User customer : customers) {
                writer.write(customer.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadBookings() {
        try (BufferedReader reader = new BufferedReader(new FileReader("bookings.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 10) {
                    Car car = new Car(parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5]));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date bookingDate = dateFormat.parse(parts[6]);
                    Date returnDate = dateFormat.parse(parts[7]);
                    CustomerBooking booking = new CustomerBooking(parts[0], car, bookingDate, returnDate, parts[8]);
                    booking.setStatus(parts[9]);
                    bookings.add(booking);
                } else {
                    System.err.println("Invalid booking data: " + line);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void saveBookings() {
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

    public void bookCar(String carId, Date pickupDate, Date returnDate) {
        for (Car car : availableCars) {
            if (car.getCarId().equals(carId) && car.isAvailable()) {
                for (CustomerBooking booking : bookings) {
                    if (booking.getCar().getCarId().equals(carId) &&
                            !(pickupDate.after(booking.getReturnDate())
                                    || returnDate.before(booking.getBookingDate()))) {
                        JOptionPane.showMessageDialog(null,
                                "The car is already booked for the selected dates. Please select different dates.");
                        return;
                    }
                }
                CustomerBooking booking = new CustomerBooking(UUID.randomUUID().toString(), car, pickupDate, returnDate,
                        currentCustomer.getUsername());
                makeBooking(booking);
                car.setAvailable(false);
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Car not found or not available.");
    }

    public void viewBookingHistory() {
        if (currentCustomer != null) {
            StringBuilder history = new StringBuilder();
            for (CustomerBooking booking : bookings) {
                if (booking.getCustomerUsername().equals(currentCustomer.getUsername())) {
                    history.append(booking.prettyString()).append("\n\n");
                }
            }
            JOptionPane.showMessageDialog(null, history.length() == 0 ? "No bookings found." : history.toString(), "Booking History", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "No customer is logged in.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
