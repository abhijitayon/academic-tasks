/*
Question 16: Multi-Level Parking Management System
10 Marks
A shopping mall operates a multi-level parking facility for motorcycles, cars, and buses. Each vehicle category has a different hourly parking charge.
Requirements:
Create an abstract class Vehicle containing private attributes registrationNumber, ownerName, and entryTime.
Create:
Motorcycle
Car
Bus
Implement calculateParkingFee(int hours).
Charges:
Motorcycle: Tk. 20/hour.
Car: Tk. 50/hour.
Bus: Tk. 100/hour.
For any partial hour, charge for a full hour.
Develop a ParkingLot class using suitable collections.
Implement:
Register a vehicle's entry.
Allocate an available parking space.
Record the vehicle's exit.
Calculate parking fees.
Display available spaces and total collected revenue.
Special condition: Prevent duplicate active entries for the same vehicle and reject new entries when parking capacity is full.
Concepts: All OOP pillars, collections, date/time handling, exception handling.
*/




import java.util.ArrayList;

abstract class Vehicle {
    private String registrationNumber, ownerName;
    private double entryTime;

    Vehicle(String reg, String owner, double time) {
        registrationNumber = reg;
        ownerName = owner;
        entryTime = time;
    }

    abstract double calculateParkingFee(int hours);

    String getRegistrationNumber() {
        return registrationNumber;
    }

    String getOwnerName() {
        return ownerName;
    }

    double getEntryTime() {
        return entryTime;
    }
}

class Motorcycle extends Vehicle {
    Motorcycle(String reg, String owner, double time) {
        super(reg, owner, time);
    }

    double calculateParkingFee(int hours) {
        return hours * 20;
    }
}

class Car extends Vehicle {
    Car(String reg, String owner, double time) {
        super(reg, owner, time);
    }

    double calculateParkingFee(int hours) {
        return hours * 50;
    }
}

class Bus extends Vehicle {
    Bus(String reg, String owner, double time) {
        super(reg, owner, time);
    }

    double calculateParkingFee(int hours) {
        return hours * 100;
    }
}

class ParkingLot {
    ArrayList<Vehicle> vehicles = new ArrayList<>();
    int capacity;
    double revenue = 0;

    ParkingLot(int capacity) {
        this.capacity = capacity;
    }

    void registerEntry(Vehicle v) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getRegistrationNumber().equals(v.getRegistrationNumber())) {
                System.out.println("Vehicle already parked.");
                return;
            }
        }

        if (vehicles.size() >= capacity) {
            System.out.println("Parking is full.");
            return;
        }

        vehicles.add(v);
        System.out.println("Parking space allocated to " + v.getRegistrationNumber());
    }

    void recordExit(String reg, double exitTime) {
        for (Vehicle v : vehicles) {
            if (v.getRegistrationNumber().equals(reg)) {
                double duration = exitTime - v.getEntryTime();

                if (duration <= 0) {
                    System.out.println("Invalid exit time.");
                    return;
                }

                int hours = (int) duration;

                if (duration > hours)
                    hours++;

                double fee = v.calculateParkingFee(hours);
                revenue += fee;

                System.out.println("Vehicle: " + reg);
                System.out.println("Owner: " + v.getOwnerName());
                System.out.println("Parking Hours: " + hours);
                System.out.println("Parking Fee: Tk. " + fee);

                vehicles.remove(v);
                return;
            }
        }

        System.out.println("Vehicle not found.");
    }

    void displayStatus() {
        System.out.println("Available Spaces: " + (capacity - vehicles.size()));
        System.out.println("Total Revenue: Tk. " + revenue);
    }
}

public class Q16 {
    public static void main(String[] args) {
        ParkingLot parking = new ParkingLot(3);

        parking.registerEntry(new Motorcycle("M101", "Fuyad", 9.0));
        parking.registerEntry(new Car("C101", "Ayon", 10.5));
        parking.registerEntry(new Bus("B101", "Hasan", 11.0));

        // Duplicate entry
        parking.registerEntry(new Car("C101", "Ayon", 12.0));

        // Parking full
        parking.registerEntry(new Motorcycle("M102", "Sakib", 12.0));

        System.out.println("\nVehicle Exits:");

        parking.recordExit("M101", 11.5);
        parking.recordExit("C101", 13.0);

        System.out.println("\nParking Status:");
        parking.displayStatus();
    }
}