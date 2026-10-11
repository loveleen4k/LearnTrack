package com.airtribe.learntrack.constants;

public class MenuOptions {

    // Shared
    public static final int BACK_OR_EXIT = 0;

    // Main menu
    public static final int MAIN_STUDENTS = 1;
    public static final int MAIN_COURSES = 2;
    public static final int MAIN_ENROLLMENTS = 3;

    // Student menu
    public static final int STUDENT_ADD = 1;
    public static final int STUDENT_VIEW_ALL = 2;
    public static final int STUDENT_VIEW_BY_BATCH = 3;
    public static final int STUDENT_SEARCH = 4;
    public static final int STUDENT_UPDATE = 5;
    public static final int STUDENT_DEACTIVATE = 6;

    // Course menu
    public static final int COURSE_ADD = 1;
    public static final int COURSE_VIEW_ALL = 2;
    public static final int COURSE_ACTIVATE = 3;
    public static final int COURSE_DEACTIVATE = 4;

    // Enrollment menu
    public static final int ENROLLMENT_ENROLL = 1;
    public static final int ENROLLMENT_VIEW_BY_STUDENT = 2;
    public static final int ENROLLMENT_COMPLETE = 3;
    public static final int ENROLLMENT_CANCEL = 4;

    private MenuOptions() {
    }
}
