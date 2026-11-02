/*
Question 7: Ride-Sharing Fare Calculation System
10 Marks
A ride-sharing company offers Bike, Car, and Premium Car services. Fare calculation depends on vehicle type and travel distance.
Requirements:
Create an interface RideService declaring:
double calculateFare(double distance);
void displayRideDetails();
Create an abstract class Vehicle containing private attributes vehicleId, driverName, and registrationNumber. Make Vehicle implement RideService.
Create three concrete subclasses with the following fare policies:
Vehicle
Base fare
Per kilometer
Bike
Tk. 30
Tk. 12
Car
Tk. 80
Tk. 25
Premium Car
Tk. 150
Tk. 40

Implement the following operations:
Register vehicles.
Select a vehicle category.
Calculate fares for different distances.
Apply a 10% surcharge when distance exceeds 20 km.
Display ride details and final fare.
Reject zero or negative travel distances.
Concepts: Interface, abstract class, inheritance, runtime polymorphism, exception handling.
*/




import java.util.ArrayList;

// Interface
interface RideService {
    double calculateFare(double distance);
    void displayRideDetails();
}

// Abstract parent class
abstract class Vehicle implements RideService {
    private int vehicleId;
    private String driverName;
    private String registrationNumber;

    Vehicle(int id, String name, String reg) {
        vehicleId = id;
        driverName = name;
        registrationNumber = reg;
    }

    int getVehicleId() {
        return vehicleId;
    }

    String getDriverName() {
        return driverName;
    }

    String getRegistrationNumber() {
        return registrationNumber;
    }

    void displayDetails() {
        System.out.println("Vehicle ID: " + vehicleId);
        System.out.println("Driver: " + driverName);
        System.out.println("Registration: " + registrationNumber);
    }
}

// Bike class
class Bike extends Vehicle {
    Bike(int id, String name, String reg) {
        super(id, name, reg);
    }

    public double calculateFare(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Invalid distance");
        }

        double fare = 30 + (distance * 12);

        if (distance > 20) {
            fare = fare * 1.10;
        }

        return fare;
    }

    public void displayRideDetails() {
        System.out.println("\n===== BIKE RIDE =====");
        displayDetails();
    }
}

// Car class
class Car extends Vehicle {
    Car(int id, String name, String reg) {
        super(id, name, reg);
    }

    public double calculateFare(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Invalid distance");
        }

        double fare = 80 + (distance * 25);

        if (distance > 20) {
            fare = fare * 1.10;
        }

        return fare;
    }

    public void displayRideDetails() {
        System.out.println("\n===== CAR RIDE =====");
        displayDetails();
    }
}

// Premium car class
class PremiumCar extends Vehicle {
    PremiumCar(int id, String name, String reg) {
        super(id, name, reg);
    }

    public double calculateFare(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Invalid distance");
        }

        double fare = 150 + (distance * 40);

        if (distance > 20) {
            fare = fare * 1.10;
        }

        return fare;
    }

    public void displayRideDetails() {
        System.out.println("\n===== PREMIUM CAR RIDE =====");
        displayDetails();
    }
}

// Ride-sharing company
class RideSharing {
    ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

    void registerVehicle(Vehicle v) {
        vehicles.add(v);
        System.out.println(v.getDriverName() + "'s vehicle registered.");
    }

    Vehicle selectVehicle(int id) {
        for (int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).getVehicleId() == id) {
                return vehicles.get(i);
            }
        }

        return null;
    }

    void calculateRide(int id, double distance) {
        Vehicle v = selectVehicle(id);

        if (v == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        v.displayRideDetails();

        System.out.println("Distance: " + distance + " km");
        System.out.println("Final Fare: Tk. " + v.calculateFare(distance));
    }
}

// Main class
public class Q07 {
    public static void main(String[] args) {
        RideSharing company = new RideSharing();

        company.registerVehicle(new Bike(101, "Ayon", "SYL-1234"));
        company.registerVehicle(new Car(102, "Dhruba", "SYL-5678"));
        company.registerVehicle(new PremiumCar(103, "Hasan", "SYL-9012"));

        company.calculateRide(101, 10);
        company.calculateRide(102, 25);
        company.calculateRide(103, 30);
    }
}