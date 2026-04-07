/**
 * Action for registering a course.
 */
public class RegisterAction extends Action {
    private final Student student;
    private final Course course;

    public RegisterAction(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @Override
    public void undo() {
        student.dropCourse(course);
    }
}
