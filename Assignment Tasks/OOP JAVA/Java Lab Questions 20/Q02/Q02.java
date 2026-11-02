/*
Question 2: University Student Management and Scholarship System
10 Marks
A university manages undergraduate and postgraduate students. Scholarship eligibility and calculation policies differ between these student categories.
Requirements:
Create an abstract class Student with private attributes studentId, name, cgpa, and semesterFee.
Create two subclasses:
UndergraduateStudent
PostgraduateStudent
Implement an abstract method calculateScholarship() using the following policies:
Student type
Condition
Scholarship
Undergraduate
CGPA ≥ 3.75
40% of semester fee
Undergraduate
3.50 ≤ CGPA < 3.75
20% of semester fee
Postgraduate
CGPA ≥ 3.75
50% of semester fee
Postgraduate
3.50 ≤ CGPA < 3.75
30% of semester fee
Both
CGPA < 3.50
No scholarship

Develop a University class using ArrayList<Student>.
Implement the following operations:
Register undergraduate and postgraduate students.
Calculate individual scholarships using runtime polymorphism.
Update a student's CGPA using their ID.
Search for students by ID.
Display all students with scholarship amounts and remaining payable fees.
Special condition: CGPA must be within 0.00–4.00. Duplicate student IDs must not be accepted.
Concepts: Abstraction, inheritance, encapsulation, polymorphism, collections, validation.
*/



import java.util.ArrayList;

// Abstract parent class
abstract class Student {   //till 89
    private int studentId;
    private String name;
    private double cgpa;
    private double semesterFee;

    Student(int id, String name, double cgpa, double fee) {
        if (cgpa < 0 || cgpa > 4.00) {
            throw new IllegalArgumentException("Invalid CGPA");
        }

        this.studentId = id;
        this.name = name;
        this.cgpa = cgpa;
        this.semesterFee = fee;
    }

    // Abstract method
    abstract double calculateScholarship();

    // Getter methods
    int getStudentId() {
        return studentId;
    }

    String getName() {
        return name;
    }

    double getCGPA() {
        return cgpa;
    }

    double getSemesterFee() {
        return semesterFee;
    }

    // Update CGPA
    void setCGPA(double cgpa) {
        if (cgpa >= 0 && cgpa <= 4.00) {
            this.cgpa = cgpa;
        }
    }
}

// Undergraduate student class
class UndergraduateStudent extends Student {

    UndergraduateStudent(int id, String name, double cgpa, double fee) {
        super(id, name, cgpa, fee);
    }

    // Calculate undergraduate scholarship
    double calculateScholarship() {
        if (getCGPA() >= 3.75) {
            return getSemesterFee() * 0.40;
        } else if (getCGPA() >= 3.50) {
            return getSemesterFee() * 0.20;
        } else {
            return 0;
        }
    }
}

// Postgraduate student class
class PostgraduateStudent extends Student {

    PostgraduateStudent(int id, String name, double cgpa, double fee) {
        super(id, name, cgpa, fee);
    }

    // Calculate postgraduate scholarship
    double calculateScholarship() {
        if (getCGPA() >= 3.75) {
            return getSemesterFee() * 0.50;
        } else if (getCGPA() >= 3.50) {
            return getSemesterFee() * 0.30;
        } else {
            return 0;
        }
    }
}

// University class
class University {
    ArrayList<Student> students = new ArrayList<Student>();

    // Register student
    void registerStudent(Student s) {

        // Check for duplicate ID
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId() == s.getStudentId()) {
                System.out.println("Student ID already exists.");
                return;
            }
        }

        students.add(s);
        System.out.println(s.getName() + " registered successfully.");
    }

    // Search student by ID
    Student searchStudent(int id) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getStudentId() == id) {
                return students.get(i);
            }
        }

        return null;
    }

    // Update student CGPA
    void updateCGPA(int id, double newCGPA) {
        Student s = searchStudent(id);

        if (s != null) {
            s.setCGPA(newCGPA);
            System.out.println("CGPA updated.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Display all students and their scholarships
    void displayStudents() {
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);

            double scholarship = s.calculateScholarship();
            double payable = s.getSemesterFee() - scholarship;

            System.out.println("\nStudent ID: " + s.getStudentId());
            System.out.println("Name: " + s.getName());
            System.out.println("CGPA: " + s.getCGPA());
            System.out.println("Semester Fee: Tk. " + s.getSemesterFee());
            System.out.println("Scholarship: Tk. " + scholarship);
            System.out.println("Payable Fee: Tk. " + payable);
        }
    }
}

// Main class
public class Q02 {
    public static void main(String[] args) {

        University university = new University();

        // Register undergraduate students
        university.registerStudent(
                new UndergraduateStudent(101, "Ayon", 3.90, 50000));

        university.registerStudent(
                new UndergraduateStudent(102, "Fuyad", 3.60, 50000));

        // Register postgraduate students
        university.registerStudent(
                new PostgraduateStudent(103, "Hasan", 3.85, 60000));

        university.registerStudent(
                new PostgraduateStudent(104, "Sakib", 3.89, 60000));

        // Update CGPA using student ID
        university.updateCGPA(104, 3.60);

        // Search for a student
        Student s = university.searchStudent(101);

        if (s != null) {
            System.out.println("\nStudent Found: " + s.getName());
        }

        // Display all students
        System.out.println("\n===== STUDENT DETAILS =====");
        university.displayStudents();
    }
}