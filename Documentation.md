# Smart Course Registration and Timetable Management System

## Project Overview

This project implements a Smart Course Registration and Timetable Management System in Java. The system allows students to register for courses, manage their timetables, and handles conflicts automatically. Admins can add courses, set capacities, and manage enrollments. The system uses various data structures and algorithms as specified in the project requirements.

## Architecture Diagram

```
+----------------+     +----------------+     +----------------+
|     Student    |     |     Course     |     |   Timetable    |
| - id           |     | - code         |     | - schedule     |
| - name         |     | - name         |     | (Map<String,   |
| - registered   |     | - capacity     |     |  List<Course>>)|
|   courses      |     | - enrolled     |     +----------------+
| - timetable    |     |   students     |           |
+----------------+     | - waiting list |           |
          |           | - schedule      |           |
          |           +----------------+           |
          |                   |                   |
          |                   |                   |
          v                   v                   v
+----------------+     +----------------+     +----------------+
|     Admin       |     |    TimeSlot    |     |     Action     |
| - courses       |     | - day          |     | (Abstract)     |
| - courseMap     |     | - startTime    |     +----------------+
| (HashMap)       |     | - endTime      |           |
+----------------+     +----------------+           |
          |                                       |
          |                                       |
          v                                       v
+----------------+                             +----------------+
|      Main       |                             | RegisterAction |
| (Console UI)    |                             | DropAction     |
+----------------+                             +----------------+
```

## Class Definitions

### TimeSlot
- **Attributes**: day (String), startTime (String), endTime (String)
- **Methods**:
  - `overlapsWith(TimeSlot other)`: Checks if two time slots overlap
  - Getters for attributes

### Course
- **Attributes**: code (String), name (String), capacity (int), enrolledStudents (List<Student>), waitingList (Queue<Student>), schedule (List<TimeSlot>)
- **Methods**:
  - `registerStudent(Student student)`: Registers student or adds to waiting list
  - `dropStudent(Student student)`: Drops student and promotes from waiting list if available
  - `isFull()`: Checks if course is at capacity
  - Getters for attributes

### Student
- **Attributes**: id (String), name (String), registeredCourses (List<Course>), timetable (Timetable)
- **Methods**:
  - `registerCourse(Course course)`: Registers for course if no conflict
  - `dropCourse(Course course)`: Drops a course
  - Getters for attributes

### Timetable
- **Attributes**: schedule (Map<String, List<Course>>)
- **Methods**:
  - `addCourse(Course course)`: Adds course to timetable
  - `removeCourse(Course course)`: Removes course from timetable
  - `hasConflict(Course course)`: Checks for scheduling conflicts
  - `display()`: Prints the timetable

### Admin
- **Attributes**: courses (List<Course>), courseMap (Map<String, Course>)
- **Methods**:
  - `addCourse(...)`: Adds a new course
  - `findCourse(String code)`: Fast lookup by course code
  - `searchCoursesByName(String name)`: Linear search for courses by name
  - `sortCoursesByName()`: Sorts courses using insertion sort
  - `displayCourses()`: Displays all courses
  - `viewEnrolledStudents(Course course)`: Shows enrolled students
  - `viewWaitingList(Course course)`: Shows waiting list

### Action (Abstract)
- **Methods**: `undo()` (abstract)

### RegisterAction extends Action
- **Attributes**: student (Student), course (Course)
- **Methods**: `undo()`: Undoes registration by dropping the course

### DropAction extends Action
- **Attributes**: student (Student), course (Course)
- **Methods**: `undo()`: Undoes drop by re-registering for the course

### Main
- **Attributes**: admin (Admin), students (Map<String, Student>), undoStack (Stack<Action>), scanner (Scanner)
- **Methods**:
  - `main(String[] args)`: Entry point
  - `studentMenu()`: Student interface
  - `adminMenu()`: Admin interface
  - Various helper methods for operations

## Data Structures Used

- **ArrayList**: For storing lists of students, courses, and time slots
- **LinkedList**: For waiting lists (Queue implementation)
- **HashMap**: For fast lookup of students by ID and courses by code
- **Stack**: For undo functionality
- **Queue**: For waiting list management

## Algorithms Implemented

- **Linear Search**: Used in `searchCoursesByName()` for finding courses by name
- **Binary Search**: Not directly implemented, but HashMap provides O(1) lookup
- **Insertion Sort**: Used in `sortCoursesByName()` for sorting courses alphabetically
- **Conflict Detection**: Custom algorithm in `hasConflict()` that checks time slot overlaps

## Implementation Details

### Conflict Detection
The system detects timetable conflicts by comparing time slots of courses. For each day, it checks if any existing course's time slots overlap with the new course's time slots using the `overlapsWith()` method in `TimeSlot`.

### Waiting List Management
When a course is full, students are added to a queue (waiting list). When a student drops the course, the first student in the waiting list is automatically enrolled.

### Undo Functionality
A stack of `Action` objects allows undoing the last registration or drop action. Each action knows how to reverse itself.

### Search and Sort
- Courses can be searched by name using linear search
- Courses can be sorted by name using insertion sort
- Fast lookup by ID/code using HashMap

## Testing

### Basic Test Cases

1. **Student Registration**:
   - Register student for a course with available slots
   - Attempt to register for conflicting time slots
   - Register for full course (should add to waiting list)

2. **Course Management**:
   - Add new course with admin
   - View enrolled students
   - View waiting list

3. **Timetable Display**:
   - Show student's registered courses and schedule

4. **Undo Operation**:
   - Register for a course, then undo
   - Drop a course, then undo

### Edge Cases

- Registering for non-existent course
- Dropping course not registered for
- Adding course with invalid time slots
- Multiple students registering simultaneously (handled by queue)

## How to Run

1. Compile all Java files:
   ```
   javac *.java
   ```

2. Run the main class:
   ```
   java Main
   ```

3. Follow the console prompts to navigate the system.

## Sample Usage

1. Start the program
2. Choose Admin Login
3. Add a course (e.g., CS101, Introduction to CS, capacity 2, Monday 9:00-10:30)
4. Go back to main menu
5. Choose Student Login with ID S001
6. Register for CS101
7. View timetable
8. Try registering for another course with conflicting time

## Conclusion

This implementation demonstrates the use of fundamental data structures and algorithms in a real-world application. The OOP design provides modularity and extensibility. The system successfully handles course registration, conflict detection, waiting lists, and administrative functions as specified in the project requirements.