/**
 * Represents a time slot for a course with day, start time, and end time.
 * Provides methods to check for overlaps with other time slots.
 */
public class TimeSlot {
    private final String day;
    private final String startTime;
    private final String endTime;

    public TimeSlot(String day, String startTime, String endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    /**
     * Checks if this time slot overlaps with another time slot.
     * @param other the other time slot to check
     * @return true if they overlap, false otherwise
     */
    public boolean overlapsWith(TimeSlot other) {
        if (!this.day.equals(other.day)) {
            return false;
        }
        // Simple time comparison assuming HH:MM format
        String[] thisStart = this.startTime.split(":");
        String[] thisEnd = this.endTime.split(":");
        String[] otherStart = other.startTime.split(":");
        String[] otherEnd = other.endTime.split(":");

        int thisStartMin = Integer.parseInt(thisStart[0]) * 60 + Integer.parseInt(thisStart[1]);
        int thisEndMin = Integer.parseInt(thisEnd[0]) * 60 + Integer.parseInt(thisEnd[1]);
        int otherStartMin = Integer.parseInt(otherStart[0]) * 60 + Integer.parseInt(otherStart[1]);
        int otherEndMin = Integer.parseInt(otherEnd[0]) * 60 + Integer.parseInt(otherEnd[1]);

        return !(thisEndMin <= otherStartMin || thisStartMin >= otherEndMin);
    }

    @Override
    public String toString() {
        return day + " " + startTime + "-" + endTime;
    }
}