import javax.swing.*;
import java.util.Date;

public class AdminSystemTest {

    public static void main(String[] args) {
        // Create an instance of AdminDashboard
        AdminDashboard dashboard = new AdminDashboard();

        // Simulate a login scenario
        dashboard.showLoginPage();

        // Simulate adding cars
        dashboard.manageCars(); // This will prompt for adding a car

        // Simulate viewing car list
        dashboard.manageCars(); // This will prompt for viewing the car list

        // Simulate approving bookings
        AdminApproval adminApproval = new AdminApproval();
        dashboard.manageBooking(adminApproval);

        // Simulate viewing bookings
        dashboard.viewBookings();

        // Simulate creating a booking
        Car car = dashboard.carList.getCars().get(0); // Assuming there's at least one car in the list
        Date bookingDate = new Date(); // Use current date for booking
        Date returnDate = new Date(); // Use current date for return
        CustomerBooking newBooking = new CustomerBooking("B001", car, bookingDate, returnDate);
        dashboard.bookings.add(newBooking);

        // Simulate viewing updated bookings
        dashboard.viewBookings();

        // Simulate editing a car
        dashboard.manageCars(); // This will prompt for editing a car

        // Test serialization and deserialization of cars
        serializeAndDeserializeCars(dashboard.carList);

        // Simulate viewing car list after edits
        dashboard.manageCars(); // This will prompt for viewing the updated car list
    }

    private static void serializeAndDeserializeCars(AdminCarList carList) {
        // Test serialization and deserialization of cars
        List<Car> originalCars = carList.getCars();

        // Serialize
        carList.setCars(originalCars);

        // Deserialize
        List<Car> deserializedCars = carList.getCars();

        // Display the deserialized cars
        StringBuilder carInfo = new StringBuilder("Deserialized Cars:\n");
        for (Car car : deserializedCars) {
            carInfo.append(car).append("\n");
        }
        JOptionPane.showMessageDialog(null, carInfo.toString());
    }
}
