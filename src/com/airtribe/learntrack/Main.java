package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final StudentService studentService = new StudentService(new StudentRepository());
    private static final CourseService courseService = new CourseService(new CourseRepository());
    private static final EnrollmentService enrollmentService =
            new EnrollmentService(new EnrollmentRepository(), studentService, courseService);

    public static void main(String[] args) {
        System.out.println("=== Welcome to " + AppConstants.APP_NAME + " ===");
        try {
            runMenu("main");
        } catch (NoSuchElementException e) {
            System.out.println("\nNo more input. Exiting.");
        }
        System.out.println("Goodbye!");
    }

    // ---------- Menu loop (shared by main and sub menus) ----------

    private static void runMenu(String menuName) {
        int choice;
        do {
            printMenu(menuName);
            choice = readMenuChoice();
            if (choice == MenuOptions.BACK_OR_EXIT) {
                break;
            }
            try {
                handleChoice(menuName, choice);
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (true);
    }

    private static int readMenuChoice() {
        try {
            return InputValidator.parseInt(prompt("Choose an option"), "Menu option");
        } catch (InvalidInputException e) {
            return -1; // -1 is not a valid option, so the menu shows "Invalid option" and repeats
        }
    }

    private static void handleChoice(String menuName, int choice) {
        switch (menuName) {
            case "main":
                handleMainChoice(choice);
                break;
            case "student":
                handleStudentChoice(choice);
                break;
            case "course":
                handleCourseChoice(choice);
                break;
            case "enrollment":
                handleEnrollmentChoice(choice);
                break;
            default:
                break;
        }
    }

    private static void printMenu(String menuName) {
        System.out.println();
        switch (menuName) {
            case "main":
                System.out.println("--- Main Menu ---");
                System.out.println("1. Student Management");
                System.out.println("2. Course Management");
                System.out.println("3. Enrollment Management");
                System.out.println("0. Exit");
                break;
            case "student":
                System.out.println("--- Student Management ---");
                System.out.println("1. Add student");
                System.out.println("2. View all students");
                System.out.println("3. View students by batch");
                System.out.println("4. Search student by ID");
                System.out.println("5. Update student");
                System.out.println("6. Deactivate student");
                System.out.println("0. Back");
                break;
            case "course":
                System.out.println("--- Course Management ---");
                System.out.println("1. Add course");
                System.out.println("2. View all courses");
                System.out.println("3. Activate course");
                System.out.println("4. Deactivate course");
                System.out.println("0. Back");
                break;
            case "enrollment":
                System.out.println("--- Enrollment Management ---");
                System.out.println("1. Enroll student in course");
                System.out.println("2. View enrollments for a student");
                System.out.println("3. Mark enrollment completed");
                System.out.println("4. Mark enrollment cancelled");
                System.out.println("0. Back");
                break;
            default:
                break;
        }
    }

    // ---------- Main menu ----------

    private static void handleMainChoice(int choice) {
        switch (choice) {
            case MenuOptions.MAIN_STUDENTS:
                runMenu("student");
                break;
            case MenuOptions.MAIN_COURSES:
                runMenu("course");
                break;
            case MenuOptions.MAIN_ENROLLMENTS:
                runMenu("enrollment");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    // ---------- Students ----------

    private static void handleStudentChoice(int choice) {
        switch (choice) {
            case MenuOptions.STUDENT_ADD:
                addStudent();
                break;
            case MenuOptions.STUDENT_VIEW_ALL:
                printStudents(studentService.listStudents());
                break;
            case MenuOptions.STUDENT_VIEW_BY_BATCH:
                printStudents(studentService.listStudents(prompt("Batch")));
                break;
            case MenuOptions.STUDENT_SEARCH:
                System.out.println(studentService.findStudentById(readId("Student ID")));
                break;
            case MenuOptions.STUDENT_UPDATE:
                updateStudent();
                break;
            case MenuOptions.STUDENT_DEACTIVATE:
                studentService.deactivateStudent(readId("Student ID"));
                System.out.println("Student deactivated.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void addStudent() {
        String firstName = prompt("First name");
        String lastName = prompt("Last name");
        String email = prompt("Email (press Enter to skip)");
        String batch = prompt("Batch");

        Student student;
        if (InputValidator.isBlank(email)) {
            student = studentService.addStudent(firstName, lastName, batch);
        } else {
            student = studentService.addStudent(firstName, lastName, email, batch);
        }
        System.out.println("Student added: " + student);
    }

    private static void updateStudent() {
        int id = readId("Student ID");
        System.out.println("Press Enter to keep the current value.");
        String firstName = prompt("New first name");
        String lastName = prompt("New last name");
        String email = prompt("New email");
        String batch = prompt("New batch");
        Student student = studentService.updateStudent(id, firstName, lastName, email, batch);
        System.out.println("Student updated: " + student);
    }

    private static void printStudents(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    // ---------- Courses ----------

    private static void handleCourseChoice(int choice) {
        switch (choice) {
            case MenuOptions.COURSE_ADD:
                addCourse();
                break;
            case MenuOptions.COURSE_VIEW_ALL:
                printCourses(courseService.listCourses());
                break;
            case MenuOptions.COURSE_ACTIVATE:
                courseService.setCourseActive(readId("Course ID"), true);
                System.out.println("Course activated.");
                break;
            case MenuOptions.COURSE_DEACTIVATE:
                courseService.setCourseActive(readId("Course ID"), false);
                System.out.println("Course deactivated.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void addCourse() {
        String name = prompt("Course name");
        String description = prompt("Description");
        int weeks = InputValidator.parseInt(prompt("Duration in weeks"), "Duration");
        Course course = courseService.addCourse(name, description, weeks);
        System.out.println("Course added: " + course);
    }

    private static void printCourses(ArrayList<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    // ---------- Enrollments ----------

    private static void handleEnrollmentChoice(int choice) {
        switch (choice) {
            case MenuOptions.ENROLLMENT_ENROLL:
                enrollStudent();
                break;
            case MenuOptions.ENROLLMENT_VIEW_BY_STUDENT:
                printEnrollments(enrollmentService.listEnrollmentsByStudent(readId("Student ID")));
                break;
            case MenuOptions.ENROLLMENT_COMPLETE:
                enrollmentService.markCompleted(readId("Enrollment ID"));
                System.out.println("Enrollment marked COMPLETED.");
                break;
            case MenuOptions.ENROLLMENT_CANCEL:
                enrollmentService.markCancelled(readId("Enrollment ID"));
                System.out.println("Enrollment marked CANCELLED.");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private static void enrollStudent() {
        int studentId = readId("Student ID");
        int courseId = readId("Course ID");
        Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
        System.out.println("Enrolled. " + enrollmentService.describe(enrollment));
    }

    private static void printEnrollments(ArrayList<Enrollment> enrollments) {
        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
            return;
        }
        for (Enrollment enrollment : enrollments) {
            System.out.println(enrollmentService.describe(enrollment));
        }
    }

    // ---------- Input helpers ----------

    private static String prompt(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine();
    }

    private static int readId(String label) {
        return InputValidator.parseInt(prompt(label), label);
    }
}
