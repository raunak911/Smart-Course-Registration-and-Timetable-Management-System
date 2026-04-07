/**
 * Action for dropping a course.
 */
public class DropAction extends Action {
    private final Student student;
    private final Course course;

    public DropAction(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    @Override
    public void undo() {
        student.registerCourse(course);
    }
}
