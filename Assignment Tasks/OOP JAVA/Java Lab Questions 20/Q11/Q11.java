/*
Question 11: University Course Enrollment System
10 Marks
A university allows students to register for Theory Courses and Laboratory Courses, each having separate credit hours and registration policies.
Requirements:
Create an abstract class Course containing private attributes courseCode, title, credit, and capacity.
Create:
TheoryCourse
LabCourse
Both classes must override calculateCourseFee().
Fee policy:
Theory Course: Tk. 2,000 per credit.
Lab Course: Tk. 3,000 per credit.
Create a Student class and maintain:
Courses using HashMap<String, Course>.
Student registration records using suitable collections.
Implement:
Add courses.
Register students in courses.
Prevent registration when capacity is full.
Prevent duplicate enrollment in the same course.
Calculate each student's total registered course fee.
Drop a registered course and update available capacity.
Concepts: All OOP pillars, HashMap, HashSet, object relationships, validation.
*/





import java.util.HashMap; //h
import java.util.HashSet;

abstract class Course {
    private String code, title;
    private int credit, capacity, enrolled = 0;

    Course(String code, String title, int credit, int capacity) {
        this.code = code;
        this.title = title;
        this.credit = credit;
        this.capacity = capacity;
    }

    abstract double calculateCourseFee();

    String getCode() { return code; }
    String getTitle() { return title; }
    int getCredit() { return credit; }

    boolean hasCapacity() { return enrolled < capacity; }
    void enroll() { enrolled++; }
    void drop() { enrolled--; }
}

class TheoryCourse extends Course {
    TheoryCourse(String c, String t, int cr, int cap) {
        super(c, t, cr, cap);
    }

    double calculateCourseFee() { return getCredit() * 2000; }
}

class LabCourse extends Course {
    LabCourse(String c, String t, int cr, int cap) {
        super(c, t, cr, cap);
    }

    double calculateCourseFee() { return getCredit() * 3000; }
}

class Student {
    int id;
    String name;
    HashSet<String> registeredCourses = new HashSet<>();

    Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class University {
    HashMap<String, Course> courses = new HashMap<>(); //h
    HashMap<Integer, Student> students = new HashMap<>(); //h

    void addCourse(Course c) {
        courses.put(c.getCode(), c); //h
    }

    void addStudent(Student s) {
        students.put(s.id, s); //h
    }

    void registerCourse(int id, String code) {
        Student s = students.get(id); //h
        Course c = courses.get(code); //h

        if (s == null || c == null) return;
        if (s.registeredCourses.contains(code) || !c.hasCapacity()) return;

        s.registeredCourses.add(code);
        c.enroll();
    }

    void dropCourse(int id, String code) {
        Student s = students.get(id); //h

        if (s != null && s.registeredCourses.remove(code)) {
            courses.get(code).drop(); //h
        }
    }

    void displayCourses(int id) {
        Student s = students.get(id); //h
        if (s == null) return;

        double total = 0;
        System.out.println("\nStudent: " + s.name);

        for (String code : s.registeredCourses) {
            Course c = courses.get(code); //h
            System.out.println(c.getTitle());
            total += c.calculateCourseFee();
        }

        System.out.println("Total Fee: Tk. " + total);
    }
}

public class Q11 {
    public static void main(String[] args) {
        University u = new University();

        u.addCourse(new TheoryCourse("CSE101", "Programming", 3, 2));
        u.addCourse(new LabCourse("CSE102", "Programming Lab", 1, 2));
        u.addCourse(new TheoryCourse("CSE103", "Data Structures", 3, 1));

        u.addStudent(new Student(101, "Ratul"));
        u.addStudent(new Student(102, "Fuyad"));

        u.registerCourse(101, "CSE101");
        u.registerCourse(101, "CSE102");
        u.registerCourse(102, "CSE101");

        u.displayCourses(101);
        u.displayCourses(102);

        u.dropCourse(101, "CSE101");
        u.registerCourse(102, "CSE103");

        u.displayCourses(102);
    }
}