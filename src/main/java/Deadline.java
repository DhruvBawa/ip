/**
 * Represents a task that must be completed by a particular date or time.
 */
public class Deadline extends Task {
    protected String dueDate;

    /**
     * Creates a deadline task with the given description and due date.
     *
     * @param description description of the task
     * @param dueDate     date or time by which the task should be completed
     */
    public Deadline(String description, String dueDate) {
        super(description);
        this.dueDate = dueDate;
    }

    /**
     * Returns the task in the format used by Larry's responses.
     *
     * @return status icon followed by the task description
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDate + ")";
    }
}
