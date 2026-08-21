/**
 * Represents a task that occurs over a specified period.
 */
public class Event extends Task {
  protected String startTime;
  protected String endTime;

  /**
   * Creates an event task with its description, start, and end.
   *
   * @param description description of the event
   * @param startTime   start of the event
   * @param endTime     end of the event
   */
  public Event(String description, String startTime, String endTime) {
    super(description);
    this.startTime = startTime;
    this.endTime = endTime;
  }

  /**
   * Returns the task in the format used by Larry's responses.
   *
   * @return status icon followed by the task description
   */
  @Override
  public String toString() {
    return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
  }
}
