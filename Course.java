import java.util.*;

/**
 * Represents a course with code, name, capacity, enrolled students, waiting list, and schedule.
 */
public class Course {
    private final String code;
    private final String name;
    private final int capacity;
    private final List<Student> enrolledStudents;
    private final Queue<Student> waitingList;
    private final List<TimeSlot> schedule;

    public Course(String code, String name, int capacity, List<TimeSlot> schedule) {
        this.code = code;
        this.name = name;
        this.capacity = capacity;
        this.enrolledStudents = new ArrayList<>();
        this.waitingList = new LinkedList<>();
        this.schedule = schedule;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Queue<Student> getWaitingList() {
        return waitingList;
    }

    public List<TimeSlot> getSchedule() {
        return schedule;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= capacity;
    }

    /**
     * Registers a student for the course.
     * If full, adds to waiting list.
     * @param student the student to register
     * @return true if registered, false if added to waiting list
     */
    public boolean registerStudent(Student student) {
        if (!isFull()) {
            enrolledStudents.add(student);
            return true;
        } else {
            waitingList.add(student);
            return false;
        }
    }

    /**
     * Drops a student from the course.
     * If waiting list has students, moves the first to enrolled.
     * @param student the student to drop
     * @return true if dropped successfully
     */
    public boolean dropStudent(Student student) {
        if (enrolledStudents.remove(student)) {
            if (!waitingList.isEmpty()) {
                Student next = waitingList.poll();
                next.registerCourse(this);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return code + ": " + name + " (Capacity: " + capacity + ", Enrolled: " + enrolledStudents.size() + ")";
    }
}