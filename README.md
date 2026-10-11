# LearnTrack

A console-based Student & Course Management System built with Core Java.
It manages students, courses and enrollments. All data is kept in memory using `ArrayList`, so it resets when the program exits.

## Features
- **Students:** add, view all, view by batch, search by ID, update, deactivate
- **Courses:** add, view all, activate, deactivate
- **Enrollments:** enroll a student in a course, view a student's enrollments, mark completed or cancelled

## Business rules
- Only active students can enroll, and only in active courses.
- A student cannot have two active enrollments in the same course.
- Only an ACTIVE enrollment can be marked completed or cancelled.
- IDs start at 1 and auto-increment.

## Project structure
```
src/com/airtribe/learntrack/
  Main.java        menu and entry point
  entity/          Person, Student, Trainer, Course, Enrollment
  repository/      in-memory ArrayList storage
  service/         business logic
  exception/       EntityNotFoundException, InvalidInputException
  util/            IdGenerator, InputValidator
  constants/       MenuOptions, AppConstants
  enums/           EnrollmentStatus
docs/              setup, JVM basics, design notes
```

## Compile and run
Requires JDK 11 or later (built with JDK 21).

From the project root:

```
mkdir -p out
javac -d out $(find src -name "*.java")
java -cp out com.airtribe.learntrack.Main
```

On Windows (PowerShell):

```
mkdir out
javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
java -cp out com.airtribe.learntrack.Main
```

Or open the folder in an IDE, mark `src` as the source root, and run `Main`.
