import javax.swing.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CustomerMainPage {
    private List<Car> availableCars;
    private List<CustomerBooking> bookings;
    private List<Customer> customers;
    private Customer currentCustomer;

    public CustomerMainPage() {
        availableCars = new ArrayList<>();
        bookings = new ArrayList<>();
        customers = new ArrayList<>();
        loadCars();
        loadCustomers();
        loadBookings();
    }

    public void viewAvailableCars() {
        StringBuilder carList = new StringBuilder();
        for (Car car : availableCars) {
            if (car.isAvailable()) {
                carList.append(car).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, carList.length() == 0 ? "No cars available." : carList.toString());
    }

    public void makeBooking(CustomerBooking booking) {
        bookings.add(booking);
        booking.makeBooking();
        saveBookings();
    }

    public void editProfile(CustomerEditProfile profile) {
        if (currentCustomer != null) {
            currentCustomer.setName(profile.getName());
            currentCustomer.setEmail(profile.getEmail());
            currentCustomer.setPhone(profile.getPhone());
            saveCustomers();
            profile.updateProfile();
            profile.showProfileInfoDialog(); // Show updated profile info using JOptionPane
        } else {
            JOptionPane.showMessageDialog(null, "No customer is logged in.");
        }
    }

    public void loadCars() {
        try (BufferedReader reader = new BufferedReader(new FileReader("car.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                Car car = new Car(parts[0], parts[1], parts[2], parts[3], Boolean.parseBoolean(parts[4]));
                availableCars.add(car);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCustomers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cust.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                customers.add(Customer.fromString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveCustomers() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cust.txt"))) {
            for (Customer customer : customers) {
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
                Car car = new Car(parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5]));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date bookingDate = dateFormat.parse(parts[6]);
                Date returnDate = dateFormat.parse(parts[7]);
                CustomerBooking booking = new CustomerBooking(parts[0], car, bookingDate, returnDate, parts[8]);
                bookings.add(booking);
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
                        booking.getCustomerEmail()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Customer login(String name, String email, String phone) {
        for (Customer customer : customers) {
            if (customer.getName().equals(name) && customer.getEmail().equals(email)
                    && customer.getPhone().equals(phone)) {
                currentCustomer = customer;
                return customer;
            }
        }
        return null;
    }

    public void register(String name, String email, String phone) {
        Customer customer = new Customer(name, email, phone);
        customers.add(customer);
        saveCustomers();
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
                        currentCustomer.getEmail());
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
                if (booking.getCustomerEmail().equals(currentCustomer.getEmail())) {
                    booking.showBookingInfoDialog(); // Modified to use JOptionPane
                }
            }
            JOptionPane.showMessageDialog(null, history.length() == 0 ? "No bookings found." : history.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No customer is logged in.");
        }
    }

    public static void main(String[] args) {
        CustomerMainPage mainPage = new CustomerMainPage();
        String[] options = { "Admin", "Customer" };
        String userType = (String) JOptionPane.showInputDialog(null, "Welcome! Are you an Admin or a Customer?",
                "User Type", JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

        if (userType != null && userType.equalsIgnoreCase("Customer")) {
            String[] actions = { "Login", "Register" };
            String action = (String) JOptionPane.showInputDialog(null, "Do you want to Login or Register?", "Action",
                    JOptionPane.QUESTION_MESSAGE, null, actions, actions[0]);

            if (action != null && action.equalsIgnoreCase("Login")) {
                String name = JOptionPane.showInputDialog("Enter your name:");
                String email = JOptionPane.showInputDialog("Enter your email:");
                String phone = JOptionPane.showInputDialog("Enter your phone:");

                Customer customer = mainPage.login(name, email, phone);
                if (customer != null) {
                    JOptionPane.showMessageDialog(null, "Login successful.");
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Please check your credentials or register.");
                    return;
                }
            } else if (action != null && action.equalsIgnoreCase("Register")) {
                String name = JOptionPane.showInputDialog("Enter your name:");
                String email = JOptionPane.showInputDialog("Enter your email:");
                String phone = JOptionPane.showInputDialog("Enter your phone:");

                mainPage.register(name, email, phone);
                JOptionPane.showMessageDialog(null, "Registration successful. You can now log in.");
                return;
            }

            boolean running = true;
            while (running) {
                String[] optionsMenu = { "Edit Profile", "Book a Car", "View Booking History", "Logout" };
                int option = JOptionPane.showOptionDialog(null, "Select an option:", "Menu", JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE, null, optionsMenu, optionsMenu[0]);

                switch (option) {
                    case 0:
                        String name = JOptionPane.showInputDialog("Enter your new name:");
                        String email = JOptionPane.showInputDialog("Enter your new email:");
                        String phone = JOptionPane.showInputDialog("Enter your new phone:");

                        CustomerEditProfile profile = new CustomerEditProfile(name, email, phone);
                        mainPage.editProfile(profile);
                        break;
                    case 1:
                        mainPage.viewAvailableCars();
                        String carId = JOptionPane.showInputDialog("Enter the car ID to book:");
                        String pickupDateStr = JOptionPane.showInputDialog("Enter the pickup date (yyyy-MM-dd):");
                        String returnDateStr = JOptionPane.showInputDialog("Enter the return date (yyyy-MM-dd):");

                        try {
                            Date pickupDate = new SimpleDateFormat("yyyy-MM-dd").parse(pickupDateStr);
                            Date returnDate = new SimpleDateFormat("yyyy-MM-dd").parse(returnDateStr);
                            mainPage.bookCar(carId, pickupDate, returnDate);
                        } catch (ParseException e) {
                            JOptionPane.showMessageDialog(null, "Invalid date format. Please try again.");
                        }
                        break;
                    case 2:
                        mainPage.viewBookingHistory();
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
                }
            }
        } else if (userType != null && userType.equalsIgnoreCase("Admin")) {
            JOptionPane.showMessageDialog(null, "Admin functionalities are not implemented in this example.");
        } else {
            JOptionPane.showMessageDialog(null, "Invalid input. Please restart the program and try again.");
        }
    }
}
