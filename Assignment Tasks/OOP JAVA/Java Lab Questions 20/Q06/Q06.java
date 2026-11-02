/*
Question 6: Employee Payroll and Tax Management System
10 Marks
A software company employs Permanent Employees, Contract Employees, and Part-Time Employees. Their salary and tax calculations differ.
Requirements:
Create an abstract class Employee with private attributes employeeId, name, and basicSalary.
Create three subclasses:
PermanentEmployee: Receives 20% house allowance and 10% medical allowance.
ContractEmployee: Receives an additional fixed allowance of Tk. 5,000.
PartTimeEmployee: Salary is calculated using hours worked × hourly rate.
Implement abstract methods:
calculateGrossSalary()
calculateTax()
Tax rules:
Gross salary up to Tk. 30,000: No tax.
Above Tk. 30,000 and up to Tk. 60,000: 5% of gross salary.
Above Tk. 60,000: 10% of gross salary.
Implement operations to register employees, generate salary slips, and display the employee receiving the highest net salary.
Concepts: Abstract classes, multiple subclasses, polymorphism, object collections, calculations.
*/




import java.util.ArrayList;

// Abstract parent class
abstract class Employee {
    private int employeeId;
    private String name;
    private double basicSalary;

    Employee(int id, String name, double basicSalary) {
        this.employeeId = id;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    abstract double calculateGrossSalary();

    abstract double calculateTax();

    int getEmployeeId() {
        return employeeId;
    }

    String getName() {
        return name;
    }

    double getBasicSalary() {
        return basicSalary;
    }

    double calculateNetSalary() {
        return calculateGrossSalary() - calculateTax();
    }

    void displaySalarySlip() {
        System.out.println("\n===== SALARY SLIP =====");
        System.out.println("Employee ID: " + employeeId);
        System.out.println("Name: " + name);
        System.out.println("Basic Salary: Tk. " + basicSalary);
        System.out.println("Gross Salary: Tk. " + calculateGrossSalary());
        System.out.println("Tax: Tk. " + calculateTax());
        System.out.println("Net Salary: Tk. " + calculateNetSalary());
    }
}

// Permanent employee
class PermanentEmployee extends Employee {
    PermanentEmployee(int id, String name, double salary) {
        super(id, name, salary);
    }

    double calculateGrossSalary() {
        return getBasicSalary() + (getBasicSalary() * 0.20)
                + (getBasicSalary() * 0.10);
    }

    double calculateTax() {
        double gross = calculateGrossSalary();

        if (gross > 60000) {
            return gross * 0.10;
        } else if (gross > 30000) {
            return gross * 0.05;
        }

        return 0;
    }
}

// Contract employee
class ContractEmployee extends Employee {
    ContractEmployee(int id, String name, double salary) {
        super(id, name, salary);
    }

    double calculateGrossSalary() {
        return getBasicSalary() + 5000;
    }

    double calculateTax() {
        double gross = calculateGrossSalary();

        if (gross > 60000) {
            return gross * 0.10;
        } else if (gross > 30000) {
            return gross * 0.05;
        }

        return 0;
    }
}

// Part-time employee
class PartTimeEmployee extends Employee {
    private int hoursWorked;
    private double hourlyRate;

    PartTimeEmployee(int id, String name, int hours, double rate) {
        super(id, name, 0);
        this.hoursWorked = hours;
        this.hourlyRate = rate;
    }

    double calculateGrossSalary() {
        return hoursWorked * hourlyRate;
    }

    double calculateTax() {
        double gross = calculateGrossSalary();

        if (gross > 60000) {
            return gross * 0.10;
        } else if (gross > 30000) {
            return gross * 0.05;
        }

        return 0;
    }
}

// Company class
class Company {
    ArrayList<Employee> employees = new ArrayList<Employee>();

    void registerEmployee(Employee e) {
        employees.add(e);
        System.out.println(e.getName() + " registered successfully.");
    }

    void generateSalarySlips() {
        for (int i = 0; i < employees.size(); i++) {
            employees.get(i).displaySalarySlip();
        }
    }

    void displayHighestNetSalary() {
        if (employees.size() == 0) {
            System.out.println("No employees registered.");
            return;
        }

        Employee highest = employees.get(0);

        for (int i = 1; i < employees.size(); i++) {
            if (employees.get(i).calculateNetSalary() >
                    highest.calculateNetSalary()) {
                highest = employees.get(i);
            }
        }

        System.out.println("\n===== HIGHEST NET SALARY =====");
        System.out.println("Employee: " + highest.getName());
        System.out.println("Employee ID: " + highest.getEmployeeId());
        System.out.println("Net Salary: Tk. " + highest.calculateNetSalary());
    }
}

// Main class
public class Q06 {
    public static void main(String[] args) {
        Company company = new Company();

        company.registerEmployee(
                new PermanentEmployee(101, "Rahim", 50000));

        company.registerEmployee(
                new ContractEmployee(102, "Karim", 40000));

        company.registerEmployee(
                new PartTimeEmployee(103, "Hasan", 100, 300));

        company.generateSalarySlips();

        company.displayHighestNetSalary();
    }
}