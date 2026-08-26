import java.time.LocalDateTime;

/**
 * Represents a task that must be completed by a particular date or time.
 */
public class Deadline extends Task {
    private final TaskDateTime dueDateTime;

    /**
     * Creates a deadline task with unparsed date and time text.
     *
     * @param description Description of the task.
     * @param dueDateText Date or time by which the task should be completed.
     */
    public Deadline(String description, String dueDateText) {
        super(description);
        this.dueDateTime = new TaskDateTime(dueDateText);
    }

    /**
     * Creates a deadline task with a parsed date and time.
     *
     * @param description Description of the task.
     * @param dueDateTime Date and time by which the task should be completed.
     */
    public Deadline(String description, LocalDateTime dueDateTime) {
        super(description);
        this.dueDateTime = new TaskDateTime(dueDateTime);
    }

    /**
     * Returns the deadline's date and time value.
     *
     * @return Date and time attached to the deadline.
     */
    public TaskDateTime getDueDateTime() {
        return dueDateTime;
    }

    /**
     * Returns the task in the format used by Larry's responses.
     *
     * @return Status icon followed by the task description.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + dueDateTime + ")";
    }
}
