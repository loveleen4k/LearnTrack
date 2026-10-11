package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;

public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // Overload 1: with email
    public Student addStudent(String firstName, String lastName, String email, String batch) {
        String validEmail = InputValidator.requireValidEmail(email);
        Student student = new Student(IdGenerator.getNextStudentId(),
                InputValidator.requireNonBlank(firstName, "First name"),
                InputValidator.requireNonBlank(lastName, "Last name"),
                validEmail,
                InputValidator.requireNonBlank(batch, "Batch"));
        studentRepository.save(student);
        return student;
    }

    // Overload 2: without email
    public Student addStudent(String firstName, String lastName, String batch) {
        Student student = new Student(IdGenerator.getNextStudentId(),
                InputValidator.requireNonBlank(firstName, "First name"),
                InputValidator.requireNonBlank(lastName, "Last name"),
                InputValidator.requireNonBlank(batch, "Batch"));
        studentRepository.save(student);
        return student;
    }

    public Student findStudentById(int id) {
        Student student = studentRepository.findById(id);
        if (student == null) {
            throw new EntityNotFoundException("Student with ID " + id + " not found.");
        }
        return student;
    }

    // Overload 1: all students
    public ArrayList<Student> listStudents() {
        return studentRepository.findAll();
    }

    // Overload 2: students in one batch
    public ArrayList<Student> listStudents(String batch) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            if (student.getBatch().equalsIgnoreCase(batch.trim())) {
                result.add(student);
            }
        }
        return result;
    }

    // Blank values keep the existing value.
    public Student updateStudent(int id, String firstName, String lastName, String email, String batch) {
        Student student = findStudentById(id);
        if (!InputValidator.isBlank(firstName)) {
            student.setFirstName(firstName.trim());
        }
        if (!InputValidator.isBlank(lastName)) {
            student.setLastName(lastName.trim());
        }
        if (!InputValidator.isBlank(email)) {
            student.setEmail(InputValidator.requireValidEmail(email));
        }
        if (!InputValidator.isBlank(batch)) {
            student.setBatch(batch.trim());
        }
        return student;
    }

    public Student deactivateStudent(int id) {
        Student student = findStudentById(id);
        student.setActive(false);
        return student;
    }

    public void removeStudent(int id) {
        studentRepository.remove(findStudentById(id));
    }
}
