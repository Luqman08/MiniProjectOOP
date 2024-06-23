import javax.swing.JOptionPane;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerBooking implements Serializable {
    private String bookingId;
    private Car car;
    private Date bookingDate;
    private Date returnDate;
    private String customerUsername; 
    private String status; 

    public CustomerBooking(String bookingId, Car car, Date bookingDate, Date returnDate, String customerUsername) {
        this.bookingId = bookingId;
        this.car = car;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
        this.customerUsername = customerUsername;
        this.status = "Pending";
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void makeBooking() {
        System.out.println("Booking made: " + this);
    }

    public void cancelBooking() {
        System.out.println("Booking cancelled: " + this);
    }

    public void viewBookingDetails() {
        System.out.println("Booking details: " + this);
    }

    @Override
    public String toString() {
        return "CustomerBooking{" +
                "bookingId='" + bookingId + '\'' +
                ", car=" + car +
                ", bookingDate=" + bookingDate +
                ", returnDate=" + returnDate +
                ", customerUsername='" + customerUsername + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public void showBookingInfoDialog() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String message = String.format(
                "Booking ID: %s\n" +
                "Car ID: %s\n" +
                "Brand: %s\n" +
                "Model: %s\n" +
                "Colour: %s\n" +
                "Booking Date: %s\n" +
                "Return Date: %s\n" +
                "Customer Username: %s\n" +
                "Status: %s",
                bookingId,
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getColour(),
                dateFormat.format(bookingDate),
                dateFormat.format(returnDate),
                customerUsername,
                status
        );

        JOptionPane.showMessageDialog(null, message, "Booking Details", JOptionPane.INFORMATION_MESSAGE);
    }

    public String prettyString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return String.format(
                "Booking ID: %s\n" +
                "Car ID: %s\n" +
                "Brand: %s\n" +
                "Model: %s\n" +
                "Colour: %s\n" +
                "Booking Date: %s\n" +
                "Return Date: %s\n" +
                "Customer Username: %s\n" +
                "Status: %s",
                bookingId,
                car.getCarId(),
                car.getBrand(),
                car.getModel(),
                car.getColour(),
                dateFormat.format(bookingDate),
                dateFormat.format(returnDate),
                customerUsername,
                status
        );
    }
}
