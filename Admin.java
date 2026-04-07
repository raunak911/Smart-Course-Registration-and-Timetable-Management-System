import java.util.*;

/**
 * Handles admin operations like adding courses, viewing students, etc.
 */
public class Admin {
    private final List<Course> courses;
    private final Map<String, Course> courseMap; // for fast lookup by code

    public Admin() {
        this.courses = new ArrayList<>();
        this.courseMap = new HashMap<>();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public Map<String, Course> getCourseMap() {
        return courseMap;
    }

    /**
     * Adds a new course.
     * @param code course code
     * @param name course name
     * @param capacity max capacity
     * @param schedule list of time slots
     */
    public void addCourse(String code, String name, int capacity, List<TimeSlot> schedule) {
        Course course = new Course(code, name, capacity, schedule);
        courses.add(course);
        courseMap.put(code, course);
    }

    /**
     * Searches for a course by code using hash map for fast lookup.
     * @param code the course code
     * @return the course or null if not found
     */
    public Course findCourse(String code) {
        return courseMap.get(code);
    }

    /**
     * Searches for courses by name using linear search.
     * @param name the course name
     * @return list of matching courses
     */
    public List<Course> searchCoursesByName(String name) {
        List<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(course);
            }
        }
        return result;
    }

    /**
     * Sorts courses by name using insertion sort.
     */
    public void sortCoursesByName() {
        for (int i = 1; i < courses.size(); i++) {
            Course key = courses.get(i);
            int j = i - 1;
            while (j >= 0 && courses.get(j).getName().compareTo(key.getName()) > 0) {
                courses.set(j + 1, courses.get(j));
                j--;
            }
            courses.set(j + 1, key);
        }
    }

    /**
     * Displays all courses.
     */
    public void displayCourses() {
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    /**
     * Views enrolled students for a course.
     * @param course the course
     */
    public void viewEnrolledStudents(Course course) {
        System.out.println("Enrolled students for " + course.getCode() + ":");
        for (Student student : course.getEnrolledStudents()) {
            System.out.println(student);
        }
    }

    /**
     * Views waiting list for a course.
     * @param course the course
     */
    public void viewWaitingList(Course course) {
        System.out.println("Waiting list for " + course.getCode() + ":");
        for (Student student : course.getWaitingList()) {
            System.out.println(student);
        }
    }
}