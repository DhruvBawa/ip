package larry.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import larry.command.AddCommand;
import larry.command.Command;
import larry.command.DateQueryCommand;
import larry.command.DeleteCommand;
import larry.command.ExitCommand;
import larry.command.FindCommand;
import larry.command.ListCommand;
import larry.command.MarkCommand;
import larry.command.UnmarkCommand;
import larry.exception.LarryException;
import larry.task.Deadline;
import larry.task.Event;
import larry.task.Task;
import larry.task.TaskDateTime;
import larry.task.Todo;

/**
 * Parses user commands into values that Larry can act on.
 */
public class Parser {
    private static final String DEADLINE_SEPARATOR = " /by ";
    private static final String EVENT_START_SEPARATOR = " /from ";
    private static final String EVENT_END_SEPARATOR = " /to ";

    /**
     * Prevents construction of a utility class.
     */
    private Parser() {
    }

    /**
     * Checks whether an input is a command keyword, optionally followed by arguments.
     *
     * @param input Full user input.
     * @param keyword Command keyword to match.
     * @return True when the input invokes the specified command.
     */
    private static boolean isCommand(String input, String keyword) {
        return input.equals(keyword) || input.startsWith(keyword + " ");
    }

    /**
     * Parses a full user command into a command that Larry can execute.
     *
     * @param command Full user command.
     * @return Command represented by the user input.
     * @throws LarryException If the command or its arguments are invalid.
     */
    public static Command parseCommand(String command) throws LarryException {
        if (command.equals("bye")) {
            return new ExitCommand();
        }
        if (command.equals("list")) {
            return new ListCommand();
        }
        if (isCommand(command, "on")) {
            return new DateQueryCommand(parseDate(command, "on"));
        }
        if (isCommand(command, "find")) {
            return new FindCommand(requireArgument(command, "find"));
        }
        if (isCommand(command, "mark")) {
            return new MarkCommand(parseTaskIndex(command, "mark"));
        }
        if (isCommand(command, "unmark")) {
            return new UnmarkCommand(parseTaskIndex(command, "unmark"));
        }
        if (isCommand(command, "delete")) {
            return new DeleteCommand(parseTaskIndex(command, "delete"));
        }
        return new AddCommand(parseTask(command));
    }

    /**
     * Converts a task-creation command into the appropriate task subtype.
     *
     * @param command Full task-creation command.
     * @return Task represented by the command.
     * @throws LarryException If the command or any required field is invalid.
     */
    private static Task parseTask(String command) throws LarryException {
        if (isCommand(command, "todo")) {
            return new Todo(requireArgument(command, "todo"));
        }

        if (isCommand(command, "deadline")) {
            return parseDeadline(command);
        }

        if (isCommand(command, "event")) {
            return parseEvent(command);
        }

        throw new LarryException();
    }

    /**
     * Parses a deadline command and validates its description and due date.
     *
     * @param command Full deadline command.
     * @return Deadline represented by the command.
     * @throws LarryException If a required field or the due date is invalid.
     */
    private static Deadline parseDeadline(String command) throws LarryException {
        String arguments = requireArgument(command, "deadline");
        int byPosition = arguments.indexOf(DEADLINE_SEPARATOR);
        int dueDatePosition = byPosition + DEADLINE_SEPARATOR.length();
        if (byPosition <= 0 || dueDatePosition >= arguments.length()) {
            throw new LarryException();
        }

        String description = arguments.substring(0, byPosition).trim();
        String dueDate = arguments.substring(dueDatePosition).trim();
        if (description.isEmpty() || dueDate.isEmpty()) {
            throw new LarryException();
        }
        try {
            return new Deadline(description, dueDate);
        } catch (DateTimeParseException e) {
            throw new LarryException();
        }
    }

    /**
     * Parses an event command and validates its description, start time, and end time.
     *
     * @param command Full event command.
     * @return Event represented by the command.
     * @throws LarryException If a required field or either date and time is invalid.
     */
    private static Event parseEvent(String command) throws LarryException {
        String arguments = requireArgument(command, "event");
        int fromPosition = arguments.indexOf(EVENT_START_SEPARATOR);
        int startTimePosition = fromPosition + EVENT_START_SEPARATOR.length();
        int toPosition = arguments.indexOf(EVENT_END_SEPARATOR, startTimePosition);
        int endTimePosition = toPosition + EVENT_END_SEPARATOR.length();
        if (fromPosition <= 0 || toPosition <= startTimePosition
                || endTimePosition >= arguments.length()) {
            throw new LarryException();
        }

        String description = arguments.substring(0, fromPosition).trim();
        String startTime = arguments.substring(startTimePosition, toPosition).trim();
        String endTime = arguments.substring(endTimePosition).trim();
        if (description.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            throw new LarryException();
        }
        try {
            return new Event(description, startTime, endTime);
        } catch (DateTimeParseException e) {
            throw new LarryException();
        }
    }

    /**
     * Parses and validates a date supplied to a command.
     *
     * @param command Full command containing the date.
     * @param keyword Command keyword preceding the date.
     * @return Parsed date.
     * @throws LarryException If the date is absent or invalid.
     */
    private static LocalDate parseDate(String command, String keyword) throws LarryException {
        String dateText = requireArgument(command, keyword);
        try {
            return TaskDateTime.parseDate(dateText);
        } catch (DateTimeParseException e) {
            throw new LarryException();
        }
    }

    /**
     * Parses a one-based task number supplied to a task command.
     *
     * @param command Full mark, unmark, or delete command.
     * @param keyword Command keyword.
     * @return Zero-based task index.
     * @throws LarryException If the task number is absent, non-numeric, zero, or negative.
     */
    private static int parseTaskIndex(String command, String keyword) throws LarryException {
        String indexText = requireArgument(command, keyword);
        try {
            int taskIndex = Integer.parseInt(indexText) - 1;
            if (taskIndex < 0) {
                throw new LarryException();
            }
            return taskIndex;
        } catch (NumberFormatException e) {
            throw new LarryException();
        }
    }

    /**
     * Extracts a required command argument and rejects blank values.
     *
     * @param command Full user command.
     * @param keyword Command keyword preceding the argument.
     * @return Trimmed argument text.
     * @throws LarryException If the argument is empty.
     */
    private static String requireArgument(String command, String keyword) throws LarryException {
        assert isCommand(command, keyword) : "Command must start with the expected keyword";
        String argument = command.substring(keyword.length()).trim();
        if (argument.isEmpty()) {
            throw new LarryException();
        }
        return argument;
    }
}
