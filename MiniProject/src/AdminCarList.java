import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminCarList {
    private List<Car> carList;
    private final String fileName = "cars.txt";

    public AdminCarList() {
        carList = new ArrayList<>();
        loadCars();
    }

    public void addCar(Car car) {
        carList.add(car);
        saveCars();
    }

    public void removeCar(Car car) {
        carList.remove(car);
        saveCars();
    }

    public void editCar(Car car) {
        for (Car c : carList) {
            if (c.getCarId().equals(car.getCarId())) {
                c.editCar(car.getCarId(), car.getBrand(), car.getModel(), car.getColour(), car.isAvailable());
                break;
            }
        }
        saveCars();
    }

    public void viewCarList() {
        StringBuilder carInfo = new StringBuilder();
        for (Car car : carList) {
            carInfo.append(car).append("\n");
        }
        JOptionPane.showMessageDialog(null, carInfo.length() == 0 ? "No cars available" : carInfo.toString());
    }

    public List<Car> getCars() {
        return carList;
    }

    public void setCars(List<Car> cars) {
        this.carList = cars;
        saveCars();
    }

    private void loadCars() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 5) {
                    String carId = parts[0];
                    String brand = parts[1];
                    String model = parts[2];
                    String colour = parts[3];
                    boolean available = Boolean.parseBoolean(parts[4]);
                    Car car = new Car(carId, brand, model, colour, available);
                    carList.add(car);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading cars from file: " + e.getMessage());
        }
    }

    private void saveCars() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Car car : carList) {
                writer.write(car.getCarId() + ":" + car.getBrand() + ":" + car.getModel() + ":" + car.getColour() + ":" + car.isAvailable());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving cars to file: " + e.getMessage());
        }
    }

    public void manageCars() {
        String[] options = {"Add Car", "Remove Car", "Edit Car", "View Cars"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Manage Cars", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (choice) {
            case 0:
                addCarDialog();
                break;
            case 1:
                removeCarDialog();
                break;
            case 2:
                editCarDialog();
                break;
            case 3:
                viewCarList();
                break;
        }
    }

    private void addCarDialog() {
        String carId = JOptionPane.showInputDialog("Enter Car ID:");
        String brand = JOptionPane.showInputDialog("Enter Car Brand:");
        String model = JOptionPane.showInputDialog("Enter Car Model:");
        String colour = JOptionPane.showInputDialog("Enter Car Colour:");
        boolean available = JOptionPane.showConfirmDialog(null, "Is the car available?") == JOptionPane.YES_OPTION;
        addCar(new Car(carId, brand, model, colour, available));
    }

    private void removeCarDialog() {
        String carId = JOptionPane.showInputDialog("Enter Car ID to remove:");
        Car carToRemove = null;
        for (Car car : carList) {
            if (car.getCarId().equals(carId)) {
                carToRemove = car;
                break;
            }
        }
        if (carToRemove != null) {
            removeCar(carToRemove);
        } else {
            JOptionPane.showMessageDialog(null, "Car not found");
        }
    }

    private void editCarDialog() {
        String carId = JOptionPane.showInputDialog("Enter Car ID to edit:");
        Car carToEdit = null;
        for (Car car : carList) {
            if (car.getCarId().equals(carId)) {
                carToEdit = car;
                break;
            }
        }
        if (carToEdit != null) {
            String brand = JOptionPane.showInputDialog("Enter Car Brand:", carToEdit.getBrand());
            String model = JOptionPane.showInputDialog("Enter Car Model:", carToEdit.getModel());
            String colour = JOptionPane.showInputDialog("Enter Car Colour:", carToEdit.getColour());
            boolean available = JOptionPane.showConfirmDialog(null, "Is the car available?", "", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            carToEdit.editCar(carId, brand, model, colour, available);
            saveCars();
        } else {
            JOptionPane.showMessageDialog(null, "Car not found");
        }
    }
}
