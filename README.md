# Smart Course Registration and Timetable Management System

## Description
This is a Java implementation of a Smart Course Registration and Timetable Management System for universities. The system allows students to register for courses, manage their schedules, and automatically detects timetable conflicts. Administrators can add courses, set capacities, and view enrollment details.

## Features
- **Student Features**:
  - Register for courses
  - Drop courses
  - View personal timetable
  - View registered courses
  - Undo last action

- **Admin Features**:
  - Add new courses with schedules
  - View all courses
  - Search courses by name
  - Sort courses by name
  - View enrolled students for a course
  - View waiting list for a course

- **System Intelligence**:
  - Automatic timetable conflict detection
  - Waiting list management with automatic promotion
  - Capacity management

## Data Structures Used
- ArrayList for dynamic lists
- LinkedList for queues (waiting lists)
- HashMap for fast lookups
- Stack for undo functionality

## Algorithms Implemented
- Linear search for course name search
- Insertion sort for sorting courses
- Custom conflict detection algorithm

## How to Compile and Run
1. Navigate to the `src` directory
2. Compile: `javac *.java`
3. Run: `java Main`

## Project Structure
- `Main.java`: Main class with console interface
- `Student.java`: Student class
- `Course.java`: Course class
- `TimeSlot.java`: Time slot representation
- `Timetable.java`: Timetable management
- `Admin.java`: Admin operations
- `Action.java`: Undo action classes
- `Documentation.md`: Detailed project documentation

## Sample Data
The system includes sample courses and students for testing:
- Courses: CS101, MATH201, PHYS101
- Students: S001 (Alice), S002 (Bob)

## Requirements
- Java 8 or higher
- Console environment for interaction