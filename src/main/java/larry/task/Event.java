package larry.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Represents a task that occurs over a specified period.
 */
public class Event extends Task {
    private final TaskDateTime startDateTime;
    private final TaskDateTime endDateTime;

    /**
     * Creates an event task from start and end date-time text.
     *
     * @param description Description of the event.
     * @param startTimeText Start date and time text accepted by Larry.
     * @param endTimeText End date and time text accepted by Larry.
     */
    public Event(String description, String startTimeText, String endTimeText) {
        super(description);
        this.startDateTime = new TaskDateTime(startTimeText);
        this.endDateTime = new TaskDateTime(endTimeText);
    }

    /**
     * Creates an event task with parsed start and end date-time values.
     *
     * @param description Description of the event.
     * @param startDateTime Start date and time of the event.
     * @param endDateTime End date and time of the event.
     */
    public Event(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super(description);
        this.startDateTime = new TaskDateTime(startDateTime);
        this.endDateTime = new TaskDateTime(endDateTime);
    }

    /**
     * Creates an event task with already parsed task date and time values.
     *
     * @param description Description of the event.
     * @param startDateTime Start date and time of the event.
     * @param endDateTime End date and time of the event.
     */
    public Event(String description, TaskDateTime startDateTime, TaskDateTime endDateTime) {
        super(description);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Returns the event's start date and time.
     *
     * @return Start date and time of the event.
     */
    public TaskDateTime getStartDateTime() {
        return startDateTime;
    }

    /**
     * Returns the event's end date and time.
     *
     * @return End date and time of the event.
     */
    public TaskDateTime getEndDateTime() {
        return endDateTime;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        Optional<LocalDateTime> startValue = startDateTime.getValue();
        Optional<LocalDateTime> endValue = endDateTime.getValue();
        if (startValue.isEmpty() || endValue.isEmpty()) {
            return false;
        }

        LocalDate startDate = startValue.get().toLocalDate();
        LocalDate endDate = endValue.get().toLocalDate();
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Returns the task in the format used by Larry's responses.
     *
     * @return Status icon followed by the task description.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startDateTime
                + " to: " + endDateTime + ")";
    }
}
