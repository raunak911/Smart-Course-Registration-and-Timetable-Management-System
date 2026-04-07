import java.util.*;

/**
 * Represents a student with ID, name, registered courses, and timetable.
 */
public class Student {
    private final String id;
    private final String name;
    private final List<Course> registeredCourses;
    private final Timetable timetable;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
        this.timetable = new Timetable();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    /**
     * Registers for a course if no conflict.
     * @param course the course to register
     * @return true if registered successfully
     */
    public boolean registerCourse(Course course) {
        if (timetable.hasConflict(course)) {
            return false;
        }
        if (course.registerStudent(this)) {
            registeredCourses.add(course);
            timetable.addCourse(course);
            return true;
        }
        return false; // added to waiting list
    }

    /**
     * Drops a course.
     * @param course the course to drop
     * @return true if dropped successfully
     */
    public boolean dropCourse(Course course) {
        if (registeredCourses.remove(course)) {
            course.dropStudent(this);
            timetable.removeCourse(course);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return id + ": " + name;
    }
}