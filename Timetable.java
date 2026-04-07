import java.util.*;

/**
 * Manages a student's timetable, checking for conflicts and storing courses by day.
 */
public class Timetable {
    private final Map<String, List<Course>> schedule; // day -> list of courses

    public Timetable() {
        this.schedule = new HashMap<>();
    }

    /**
     * Adds a course to the timetable.
     * @param course the course to add
     */
    public void addCourse(Course course) {
        for (TimeSlot slot : course.getSchedule()) {
            schedule.computeIfAbsent(slot.getDay(), k -> new ArrayList<>()).add(course);
        }
    }

    /**
     * Removes a course from the timetable.
     * @param course the course to remove
     */
    public void removeCourse(Course course) {
        for (TimeSlot slot : course.getSchedule()) {
            List<Course> dayCourses = schedule.get(slot.getDay());
            if (dayCourses != null) {
                dayCourses.remove(course);
            }
        }
    }

    /**
     * Checks if adding the course would cause a conflict.
     * @param course the course to check
     * @return true if conflict exists
     */
    public boolean hasConflict(Course course) {
        for (TimeSlot newSlot : course.getSchedule()) {
            List<Course> dayCourses = schedule.get(newSlot.getDay());
            if (dayCourses != null) {
                for (Course existing : dayCourses) {
                    for (TimeSlot existingSlot : existing.getSchedule()) {
                        if (newSlot.overlapsWith(existingSlot)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Displays the timetable.
     */
    public void display() {
        if (schedule.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }
        for (Map.Entry<String, List<Course>> entry : schedule.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (Course course : entry.getValue()) {
                System.out.println("  " + course.getCode() + " - " + course.getName());
                for (TimeSlot slot : course.getSchedule()) {
                    if (slot.getDay().equals(entry.getKey())) {
                        System.out.println("    " + slot);
                    }
                }
            }
        }
    }
}