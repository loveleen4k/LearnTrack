# JVM Basics

## JDK, JRE, JVM
- **JVM (Java Virtual Machine):** the program that runs Java bytecode on your computer.
- **JRE (Java Runtime Environment):** the JVM plus the standard libraries needed to run Java programs.
- **JDK (Java Development Kit):** the JRE plus developer tools such as `javac` (the compiler). You need the JDK to write and compile Java.

JDK contains JRE, and JRE contains JVM.

## Bytecode
When you compile a `.java` file, `javac` does not produce machine code for your specific computer. It produces bytecode in a `.class` file, a compact instruction set that the JVM understands.

## Write once, run anywhere
Because Java compiles to bytecode, the same `.class` file can run on Windows, macOS or Linux, as long as a JVM is installed there.

Each operating system has its own JVM that translates the bytecode for that machine. The developer writes and compiles once, and the JVM handles the platform differences.
