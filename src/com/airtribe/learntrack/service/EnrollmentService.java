package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.util.IdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;

public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             StudentService studentService,
                             CourseService courseService) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    public Enrollment enrollStudent(int studentId, int courseId) {
        Student student = studentService.findStudentById(studentId);
        Course course = courseService.findCourseById(courseId);

        if (!student.isActive()) {
            throw new InvalidInputException("Student " + studentId + " is deactivated.");
        }
        if (!course.isActive()) {
            throw new InvalidInputException("Course " + courseId + " is not active.");
        }
        if (hasActiveEnrollment(studentId, courseId)) {
            throw new InvalidInputException("Student is already enrolled in this course.");
        }

        Enrollment enrollment = new Enrollment(IdGenerator.getNextEnrollmentId(),
                studentId, courseId, LocalDate.now());
        enrollmentRepository.save(enrollment);
        return enrollment;
    }

    public ArrayList<Enrollment> listEnrollmentsByStudent(int studentId) {
        studentService.findStudentById(studentId); // throws if student does not exist
        return enrollmentRepository.findByStudentId(studentId);
    }

    public Enrollment markCompleted(int enrollmentId) {
        return changeStatus(enrollmentId, EnrollmentStatus.COMPLETED);
    }

    public Enrollment markCancelled(int enrollmentId) {
        return changeStatus(enrollmentId, EnrollmentStatus.CANCELLED);
    }

    public String describe(Enrollment enrollment) {
        Course course = courseService.findCourseById(enrollment.getCourseId());
        return "Enrollment ID: " + enrollment.getId() + " | Course: " + course.getCourseName()
                + " | Date: " + enrollment.getEnrollmentDate()
                + " | Status: " + enrollment.getStatus();
    }

    // Package-private: only used inside the service package
    boolean hasActiveEnrollment(int studentId, int courseId) {
        for (Enrollment e : enrollmentRepository.findByStudentId(studentId)) {
            if (e.getCourseId() == courseId && e.getStatus() == EnrollmentStatus.ACTIVE) {
                return true;
            }
        }
        return false;
    }

    private Enrollment changeStatus(int enrollmentId, EnrollmentStatus newStatus) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId);
        if (enrollment == null) {
            throw new EntityNotFoundException("Enrollment with ID " + enrollmentId + " not found.");
        }
        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new InvalidInputException("Enrollment is already " + enrollment.getStatus() + ".");
        }
        enrollment.setStatus(newStatus);
        return enrollment;
    }
}
