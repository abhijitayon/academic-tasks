/*
Question 14: Vehicle Rental and Fleet Management System
10 Marks
A rental company provides cars, motorcycles, and trucks. Rental charges vary by vehicle category and rental duration.
Requirements:
Create an abstract class RentalVehicle containing private attributes vehicleId, model, available, and dailyRent.
Create subclasses:
Car
Motorcycle
Truck
Implement calculateRentalCost(int days) with these policies:
Vehicle
Daily rate
Additional charge
Car
Tk. 3,000
None
Motorcycle
Tk. 800
None
Truck
Tk. 5,000
Tk. 1,000 fixed service fee per rental

For rentals longer than 7 days, provide a 10% discount on the daily-rent portion.
Create a RentalAgency class using ArrayList<RentalVehicle>.
Implement:
Register vehicles.
Search available vehicles.
Rent a vehicle.
Return a vehicle.
Calculate rental charges and maintain rental records.
Special condition: The same vehicle cannot be rented to multiple customers at the same time.
Concepts: Inheritance, abstract methods, encapsulation, polymorphism, ArrayList, exception handling.
*/





import java.util.ArrayList;

abstract class RentalVehicle {
    private String vehicleId, model;
    private boolean available = true;
    private double dailyRent;

    RentalVehicle(String id, String model, double rent) {
        vehicleId = id;
        this.model = model;
        dailyRent = rent;
    }

    abstract double calculateRentalCost(int days);

    String getId() { return vehicleId; }
    String getModel() { return model; }
    boolean isAvailable() { return available; }
    void setAvailable(boolean status) { available = status; }

    double getDailyRent() { return dailyRent; }
}

class Car extends RentalVehicle {
    Car(String id, String model) {
        super(id, model, 3000);
    }

    double calculateRentalCost(int days) {
        double cost = getDailyRent() * days;
        return days > 7 ? cost * 0.9 : cost;
    }
}

class Motorcycle extends RentalVehicle {
    Motorcycle(String id, String model) {
        super(id, model, 800);
    }

    double calculateRentalCost(int days) {
        double cost = getDailyRent() * days;
        return days > 7 ? cost * 0.9 : cost;
    }
}

class Truck extends RentalVehicle {
    Truck(String id, String model) {
        super(id, model, 5000);
    }

    double calculateRentalCost(int days) {
        double cost = getDailyRent() * days;
        if (days > 7) cost *= 0.9;
        return cost + 1000;
    }
}

class RentalAgency {
    ArrayList<RentalVehicle> vehicles = new ArrayList<>();
    ArrayList<String> records = new ArrayList<>();

    void registerVehicle(RentalVehicle v) {
        vehicles.add(v);
    }

    void searchAvailable() {
        for (RentalVehicle v : vehicles) {
            if (v.isAvailable())
                System.out.println(v.getId() + " - " + v.getModel());
        }
    }

    void rentVehicle(String id, int days) {
        for (RentalVehicle v : vehicles) {
            if (v.getId().equals(id) && v.isAvailable() && days > 0) {
                v.setAvailable(false);
                double cost = v.calculateRentalCost(days);
                records.add(id + " | " + days + " days | Tk. " + cost);
                System.out.println("Rental cost: Tk. " + cost);
                return;
            }
        }
        System.out.println("Vehicle unavailable or invalid rental.");
    }

    void returnVehicle(String id) {
        for (RentalVehicle v : vehicles) {
            if (v.getId().equals(id) && !v.isAvailable()) {
                v.setAvailable(true);
                System.out.println("Vehicle returned.");
                return;
            }
        }
        System.out.println("Rental record not found.");
    }

    void displayRecords() {
        for (String r : records)
            System.out.println(r);
    }
}

public class Q14 {
    public static void main(String[] args) {
        RentalAgency agency = new RentalAgency();

        agency.registerVehicle(new Car("C101", "Toyota"));
        agency.registerVehicle(new Motorcycle("M101", "Yamaha"));
        agency.registerVehicle(new Truck("T101", "Volvo"));

        System.out.println("Available Vehicles:");
        agency.searchAvailable();

        System.out.println("\nRenting Vehicles:");
        agency.rentVehicle("C101", 5);
        agency.rentVehicle("T101", 10);

        System.out.println("\nTrying to rent the same car again:");
        agency.rentVehicle("C101", 3);

        System.out.println("\nRental Records:");
        agency.displayRecords();

        System.out.println("\nReturning Car:");
        agency.returnVehicle("C101");

        System.out.println("\nAvailable Vehicles:");
        agency.searchAvailable();
    }
}