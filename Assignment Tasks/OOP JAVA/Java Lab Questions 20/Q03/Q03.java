/*
Question 3: Hospital Patient Billing and Management System
10 Marks
A hospital provides treatment to two categories of patients: InPatient and OutPatient. Their billing procedures are different.
Requirements:
Design an abstract class Patient containing private attributes patientId, name, age, and doctorFee.
Create two subclasses:
InPatient: Requires room charge per day and number of admitted days.
OutPatient: Requires consultation charge and diagnostic test charge.
Implement the abstract method calculateBill().
Billing rules:
InPatient bill = Doctor fee + (Room charge × Admitted days).
OutPatient bill = Doctor fee + Consultation charge + Diagnostic test charge.
InPatients staying more than 7 days receive a 10% discount on room charges only.
Develop a Hospital class to perform:
Admit or register patients.
Store patients using ArrayList<Patient>.
Search for a patient by ID.
Generate individual patient bills.
Calculate the hospital's total revenue from all recorded patient bills.
Special condition: Prevent negative charges and invalid admission durations.
Concepts: All OOP pillars, polymorphic billing, ArrayList, exception handling.
*/





import java.util.ArrayList;

// Abstract parent class
abstract class Patient {         // line 55
    private int patientId;
    private String name;
    private int age;
    private double doctorFee;

    Patient(int id, String name, int age, double doctorFee) {
        if (doctorFee < 0) {
            throw new IllegalArgumentException("Invalid doctor fee");
        }

        this.patientId = id;
        this.name = name;
        this.age = age;
        this.doctorFee = doctorFee;
    }

    abstract double calculateBill();

    int getPatientId() { return patientId; }
    String getName() { return name; }
    int getAge() { return age; }
    double getDoctorFee() { return doctorFee; }
}

// Inpatient class
class InPatient extends Patient {
    private double roomCharge;
    private int admittedDays;

    InPatient(int id, String name, int age, double doctorFee, double roomCharge, int admittedDays) {
        super(id, name, age, doctorFee);

        if (roomCharge < 0 || admittedDays <= 0) {
            throw new IllegalArgumentException("Invalid room charge or admission duration");
        }

        this.roomCharge = roomCharge;
        this.admittedDays = admittedDays;
    }

    double calculateBill() {
        double totalRoomCharge = roomCharge * admittedDays;

        if (admittedDays > 7) {
            totalRoomCharge = totalRoomCharge * 0.90;
        }

        return getDoctorFee() + totalRoomCharge;
    }
}

// Outpatient class
class OutPatient extends Patient {
    private double consultationCharge;
    private double diagnosticCharge;

    OutPatient(int id, String name, int age, double doctorFee,
               double consultationCharge, double diagnosticCharge) {
        super(id, name, age, doctorFee);

        if (consultationCharge < 0 || diagnosticCharge < 0) {
            throw new IllegalArgumentException("Invalid charges");
        }

        this.consultationCharge = consultationCharge;
        this.diagnosticCharge = diagnosticCharge;
    }

    double calculateBill() {
        return getDoctorFee() + consultationCharge + diagnosticCharge;
    }
}

// Hospital class
class Hospital {
    ArrayList<Patient> patients = new ArrayList<Patient>();

    void registerPatient(Patient p) {
        patients.add(p);
        System.out.println(p.getName() + " registered successfully.");
    }

    Patient searchPatient(int id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getPatientId() == id) {
                return patients.get(i);
            }
        }

        return null;
    }

    void generateBill(int id) {
        Patient p = searchPatient(id);

        if (p != null) {
            System.out.println("\n===== PATIENT BILL =====");
            System.out.println("Patient ID: " + p.getPatientId());
            System.out.println("Name: " + p.getName());
            System.out.println("Age: " + p.getAge());
            System.out.println("Doctor Fee: Tk. " + p.getDoctorFee());
            System.out.println("Total Bill: Tk. " + p.calculateBill());
        } else {
            System.out.println("Patient not found.");
        }
    }

    double calculateTotalRevenue() {
        double total = 0;

        for (int i = 0; i < patients.size(); i++) {
            total = total + patients.get(i).calculateBill();
        }

        return total;
    }
}

// Main class
public class Q03 {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();

        hospital.registerPatient(
                new InPatient(101, "Ratul", 35, 1000, 2000, 10));

        hospital.registerPatient(
                new InPatient(102, "Fuyad", 45, 1500, 1800, 5));

        hospital.registerPatient(
                new OutPatient(103, "Shoriful", 25, 500, 1000, 2000));

        hospital.registerPatient(
                new OutPatient(104, "Sakib", 30, 700, 1200, 1500));

        hospital.generateBill(101);
        hospital.generateBill(103);
        hospital.generateBill(104);

        System.out.println("\nTotal Hospital Revenue: Tk. "
                + hospital.calculateTotalRevenue());
    }
}