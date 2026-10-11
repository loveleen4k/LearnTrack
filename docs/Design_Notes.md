# Design Notes

## Why ArrayList instead of an array
An array has a fixed size, so we would need to guess how many students, courses and enrollments there will be. An `ArrayList` grows automatically when we `add` items and has helpful methods like `add`, `remove`, `size` and `isEmpty`. That suits data that keeps changing.

## Where static members are used and why
- `IdGenerator` has static counters (`studentIdCounter`, `courseIdCounter`, `enrollmentIdCounter`) and static methods (`getNextStudentId()`, etc.). The counter belongs to the whole program, not to one object, so every new entity gets a unique ID without needing an `IdGenerator` object.
- `InputValidator` has static helper methods (`parseInt`, `requireNonBlank`, ...) because they hold no state and are used everywhere.
- `AppConstants` and `MenuOptions` hold `static final` constants, which avoids "magic numbers" in the menu code.

## Where inheritance is used and what we gained
- `Student` and `Trainer` extend `Person`. Shared fields (`id`, `firstName`, `lastName`, `email`) and their getters/setters live in one place instead of being copied.
- Both call `super(...)` in their constructors.
- Both override `getDisplayName()`, so the same call gives different output depending on the object type (simple polymorphism).

## Other design choices
- **Layers:** `Main` only shows menus and reads input. Services hold the business rules. Repositories only store and fetch data.
- **Encapsulation:** all entity fields are private, accessed through getters and setters.
- **Constructor overloading:** `Student` can be created with or without an email. `StudentService.addStudent` and `listStudents` are overloaded too.
- **Deactivate, not delete:** students and courses are marked inactive instead of being removed, so existing enrollments stay valid.
- **Exceptions:** `EntityNotFoundException` for missing IDs, `InvalidInputException` for bad input or rule violations. `Main` catches them and prints a clean message.
