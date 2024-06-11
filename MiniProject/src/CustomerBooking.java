import java.util.Date;

public class CustomerBooking {
    private String bookingId;
    private Car car;
    private Date bookingDate;
    private Date returnDate;

    public CustomerBooking(String bookingId, Car car, Date bookingDate, Date returnDate) {
        this.bookingId = bookingId;
        this.car = car;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
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

    public void createBooking(String bookingId, Car car, Date bookingDate, Date returnDate) {
        // Implement create booking logic here
    }

    public void viewBooking() {
        // Implement view booking logic here
    }

    public void editBooking(String bookingId) {
        // Implement edit booking logic here
    }

    public void viewCar(Car car) {
        // Implement view car logic here
    }

    public void setBookingPickupDate(Date pickupDate) {
        // Implement set booking pickup date logic here
    }

    public void setBookingDropDate(Date dropDate) {
        // Implement set booking drop date logic here
    }
}
