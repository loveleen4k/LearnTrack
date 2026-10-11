package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.util.IdGenerator;
import com.airtribe.learntrack.util.InputValidator;

import java.util.ArrayList;

public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course addCourse(String courseName, String description, int durationInWeeks) {
        Course course = new Course(IdGenerator.getNextCourseId(),
                InputValidator.requireNonBlank(courseName, "Course name"),
                InputValidator.requireNonBlank(description, "Description"),
                InputValidator.requirePositive(durationInWeeks, "Duration"));
        courseRepository.save(course);
        return course;
    }

    public Course findCourseById(int id) {
        Course course = courseRepository.findById(id);
        if (course == null) {
            throw new EntityNotFoundException("Course with ID " + id + " not found.");
        }
        return course;
    }

    public ArrayList<Course> listCourses() {
        return courseRepository.findAll();
    }

    public Course setCourseActive(int id, boolean active) {
        Course course = findCourseById(id);
        course.setActive(active);
        return course;
    }
}
