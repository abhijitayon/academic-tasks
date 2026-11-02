/*
Question 9: Hotel Room Reservation and Billing System
10 Marks
A hotel offers Standard, Deluxe, and Suite rooms. Customers can reserve rooms for a specified number of nights.
Requirements:
Create an abstract class HotelRoom containing private attributes roomNumber, roomType, and available.
Create subclasses:
StandardRoom: Tk. 2,000 per night.
DeluxeRoom: Tk. 3,500 per night.
SuiteRoom: Tk. 6,000 per night.
Override calculateRoomCharge(int nights) in each subclass.
Implement:
Add rooms to the hotel.
Search available rooms by category.
Reserve rooms for customers.
Calculate accommodation bills.
Cancel reservations and release rooms.
Provide a 15% discount on room charges when the reservation exceeds 5 nights.
Special condition: The system must prevent two customers from reserving the same room simultaneously.
Concepts: Abstraction, inheritance, polymorphism, encapsulation, ArrayList, custom exceptions.
*/




import java.util.ArrayList;

// Abstract parent class
abstract class HotelRoom {
    private int roomNumber;
    private String roomType;
    private boolean available;

    HotelRoom(int number, String type) {
        roomNumber = number;
        roomType = type;
        available = true;
    }

    abstract double calculateRoomCharge(int nights);

    int getRoomNumber() {
        return roomNumber;
    }

    String getRoomType() {
        return roomType;
    }

    boolean isAvailable() {
        return available;
    }

    void setAvailable(boolean available) {
        this.available = available;
    }
}

// Standard room
class StandardRoom extends HotelRoom {
    StandardRoom(int number) {
        super(number, "Standard");
    }

    double calculateRoomCharge(int nights) {
        double charge = 2000 * nights;

        if (nights > 5) {
            charge = charge * 0.85;
        }

        return charge;
    }
}

// Deluxe room
class DeluxeRoom extends HotelRoom {
    DeluxeRoom(int number) {
        super(number, "Deluxe");
    }

    double calculateRoomCharge(int nights) {
        double charge = 3500 * nights;

        if (nights > 5) {
            charge = charge * 0.85;
        }

        return charge;
    }
}

// Suite room
class SuiteRoom extends HotelRoom {
    SuiteRoom(int number) {
        super(number, "Suite");
    }

    double calculateRoomCharge(int nights) {
        double charge = 6000 * nights;

        if (nights > 5) {
            charge = charge * 0.85;
        }

        return charge;
    }
}

// Reservation record
class Reservation {
    String customerName;
    HotelRoom room;
    int nights;

    Reservation(String name, HotelRoom room, int nights) {
        customerName = name;
        this.room = room;
        this.nights = nights;
    }
}

// Hotel class
class Hotel {
    ArrayList<HotelRoom> rooms = new ArrayList<HotelRoom>();
    ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    void addRoom(HotelRoom room) {
        rooms.add(room);
    }

    void searchAvailableRooms(String type) {
        for (int i = 0; i < rooms.size(); i++) {
            HotelRoom room = rooms.get(i);

            if (room.getRoomType().equalsIgnoreCase(type)
                    && room.isAvailable()) {
                System.out.println("Available Room: " + room.getRoomNumber());
            }
        }
    }

    void reserveRoom(String name, int roomNumber, int nights) {
        for (int i = 0; i < rooms.size(); i++) {
            HotelRoom room = rooms.get(i);

            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.setAvailable(false);
                reservations.add(new Reservation(name, room, nights));

                System.out.println("Room reserved for " + name);
                return;
            }
        }

        System.out.println("Room not available.");
    }

    void calculateBill(int roomNumber) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);

            if (r.room.getRoomNumber() == roomNumber) {
                System.out.println("Customer: " + r.customerName);
                System.out.println("Room Type: " + r.room.getRoomType());
                System.out.println("Nights: " + r.nights);
                System.out.println("Total Bill: Tk. "
                        + r.room.calculateRoomCharge(r.nights));
                return;
            }
        }

        System.out.println("Reservation not found.");
    }

    void cancelReservation(int roomNumber) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);

            if (r.room.getRoomNumber() == roomNumber) {
                r.room.setAvailable(true);
                reservations.remove(i);
                System.out.println("Reservation cancelled.");
                return;
            }
        }

        System.out.println("Reservation not found.");
    }
}

// Main class
public class Q09 {
    public static void main(String[] args) {
        Hotel hotel = new Hotel();

        hotel.addRoom(new StandardRoom(101));
        hotel.addRoom(new StandardRoom(102));
        hotel.addRoom(new DeluxeRoom(201));
        hotel.addRoom(new SuiteRoom(301));

        hotel.searchAvailableRooms("Standard");

        hotel.reserveRoom("Ayon", 101, 7);
        hotel.reserveRoom("Fuyad", 101, 3);

        hotel.calculateBill(101);

        hotel.cancelReservation(101);

        hotel.searchAvailableRooms("Standard");
    }
}