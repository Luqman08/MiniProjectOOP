public class Car {
    private String carId;
    private String brand;
    private String model;
    private String colour;
    private boolean available;

    public Car(String carId, String brand, String model, String colour, boolean available) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.available = available;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void editCar(String carId, String brand, String model, String colour, boolean available) {
        // Implement edit car logic here
    }
}
