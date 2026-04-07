import java.util.*;

/**
 * Main class for the Smart Course Registration and Timetable Management System.
 * Provides console-based interface for students and admins.
 */
public class Main {
    private static final Admin admin = new Admin();
    private static final Map<String, Student> students = new HashMap<>();
    private static final Stack<Action> undoStack = new Stack<>();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Sample data
        initializeSampleData();

        while (true) {
            System.out.println("\n=== Smart Course Registration System ===");
            System.out.println("1. Student Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume invalid input
                continue;
            }

            switch (choice) {
                case 1 -> studentMenu();
                case 2 -> adminMenu();
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void initializeSampleData() {
        // Sample courses
        List<TimeSlot> cs101Schedule = Arrays.asList(new TimeSlot("Monday", "09:00", "10:30"));
        admin.addCourse("CS101", "Introduction to Computer Science", 2, cs101Schedule);

        List<TimeSlot> math201Schedule = Arrays.asList(new TimeSlot("Tuesday", "10:00", "11:30"));
        admin.addCourse("MATH201", "Calculus II", 1, math201Schedule);

        List<TimeSlot> phys101Schedule = Arrays.asList(new TimeSlot("Monday", "10:00", "11:30"));
        admin.addCourse("PHYS101", "Physics I", 2, phys101Schedule);

        // Sample students
        Student s1 = new Student("S001", "Alice");
        Student s2 = new Student("S002", "Bob");
        students.put("S001", s1);
        students.put("S002", s2);
    }

    private static void studentMenu() {
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();
        Student student = students.get(id);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        System.out.println("Welcome, " + student.getName());

        while (true) {
            System.out.println("\n--- Student Menu ---");
            System.out.println("1. Register for a course");
            System.out.println("2. Drop a course");
            System.out.println("3. View registered courses");
            System.out.println("4. View timetable");
            System.out.println("5. Undo last action");
            System.out.println("6. Back to main menu");
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> registerCourse(student);
                case 2 -> dropCourse(student);
                case 3 -> viewRegisteredCourses(student);
                case 4 -> student.getTimetable().display();
                case 5 -> undoLastAction();
                case 6 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void registerCourse(Student student) {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course course = admin.findCourse(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        if (student.registerCourse(course)) {
            System.out.println("Registered successfully.");
            undoStack.push(new RegisterAction(student, course));
        } else if (course.getWaitingList().contains(student)) {
            System.out.println("Added to waiting list.");
        } else {
            System.out.println("Timetable conflict or course full.");
        }
    }

    private static void dropCourse(Student student) {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course course = admin.findCourse(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        if (student.dropCourse(course)) {
            System.out.println("Dropped successfully.");
            undoStack.push(new DropAction(student, course));
        } else {
            System.out.println("Not registered for this course.");
        }
    }

    private static void viewRegisteredCourses(Student student) {
        System.out.println("Registered courses:");
        for (Course course : student.getRegisteredCourses()) {
            System.out.println(course);
        }
    }

    private static void undoLastAction() {
        if (undoStack.isEmpty()) {
            System.out.println("No actions to undo.");
            return;
        }
        Action action = undoStack.pop();
        action.undo();
        System.out.println("Last action undone.");
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add course");
            System.out.println("2. View all courses");
            System.out.println("3. Search courses by name");
            System.out.println("4. Sort courses by name");
            System.out.println("5. View enrolled students for a course");
            System.out.println("6. View waiting list for a course");
            System.out.println("7. Back to main menu");
            System.out.print("Choose an option: ");
            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> addCourse();
                case 2 -> admin.displayCourses();
                case 3 -> searchCourses();
                case 4 -> {
                    admin.sortCoursesByName();
                    System.out.println("Courses sorted.");
                }
                case 5 -> viewEnrolled();
                case 6 -> viewWaitingList();
                case 7 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private static void addCourse() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter capacity: ");
        int capacity;
        try {
            capacity = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for capacity. Please enter a number.");
            scanner.nextLine();
            return;
        }
        System.out.print("Enter number of time slots: ");
        int numSlots;
        try {
            numSlots = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input for number of time slots. Please enter a number.");
            scanner.nextLine();
            return;
        }
        List<TimeSlot> schedule = new ArrayList<>();
        for (int i = 0; i < numSlots; i++) {
            System.out.print("Enter day: ");
            String day = scanner.nextLine();
            System.out.print("Enter start time (HH:MM): ");
            String start = scanner.nextLine();
            System.out.print("Enter end time (HH:MM): ");
            String end = scanner.nextLine();
            schedule.add(new TimeSlot(day, start, end));
        }
        admin.addCourse(code, name, capacity, schedule);
        System.out.println("Course added.");
    }

    private static void searchCourses() {
        System.out.print("Enter course name to search: ");
        String name = scanner.nextLine();
        List<Course> results = admin.searchCoursesByName(name);
        if (results.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            for (Course course : results) {
                System.out.println(course);
            }
        }
    }

    private static void viewEnrolled() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course course = admin.findCourse(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        admin.viewEnrolledStudents(course);
    }

    private static void viewWaitingList() {
        System.out.print("Enter course code: ");
        String code = scanner.nextLine();
        Course course = admin.findCourse(code);
        if (course == null) {
            System.out.println("Course not found.");
            return;
        }
        admin.viewWaitingList(course);
    }
}