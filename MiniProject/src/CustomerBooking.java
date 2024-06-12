import java.util.Date;

public class CustomerBooking {
    private String bookingId;
    private Car car;
    private Date bookingDate;
    private Date returnDate;

    // Default constructor for CustomerBooking
    public CustomerBooking() {
    }

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
        this.bookingId = bookingId;
        this.car = car;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
        // Implement create booking logic here
    }

    public void viewBooking() {
        // Implement view booking logic here
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Car: " + car.getBrand() + " " + car.getModel());
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("Return Date: " + returnDate);
    }

    public void editBooking(String bookingId, Car car, Date bookingDate, Date returnDate) {
        this.bookingId = bookingId;
        this.car = car;
        this.bookingDate = bookingDate;
        this.returnDate = returnDate;
        // Implement edit booking logic here
    }

    public void viewCar() {
        // Implement view car logic here
        System.out.println("Car ID: " + car.getCarId());
        System.out.println("Brand: " + car.getBrand());
        System.out.println("Model: " + car.getModel());
        System.out.println("Colour: " + car.getColour());
        System.out.println("Available: " + car.isAvailable());
    }

    public void setBookingPickupDate(Date pickupDate) {
        this.bookingDate = pickupDate;
        // Implement set booking pickup date logic here
    }

    public void setBookingDropDate(Date dropDate) {
        this.returnDate = dropDate;
        // Implement set booking drop date logic here
    }
}
