import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user commands into values that Larry can act on.
 */
public class Parser {
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
    public static boolean isCommand(String input, String keyword) {
        return input.equals(keyword) || input.startsWith(keyword + " ");
    }

    /**
     * Converts a task-creation command into the appropriate task subtype.
     *
     * @param command Full task-creation command.
     * @return Task represented by the command.
     * @throws LarryException If the command or any required field is invalid.
     */
    public static Task parseTask(String command) throws LarryException {
        if (isCommand(command, "todo")) {
            return new Todo(requireArgument(command, "todo"));
        }

        if (isCommand(command, "deadline")) {
            String arguments = requireArgument(command, "deadline");
            int byPosition = arguments.indexOf(" /by ");
            if (byPosition <= 0 || byPosition + 5 >= arguments.length()) {
                throw new LarryException();
            }

            String description = arguments.substring(0, byPosition).trim();
            String dueDate = arguments.substring(byPosition + 5).trim();
            if (description.isEmpty() || dueDate.isEmpty()) {
                throw new LarryException();
            }
            try {
                return new Deadline(description, dueDate);
            } catch (DateTimeParseException e) {
                throw new LarryException();
            }
        }

        if (isCommand(command, "event")) {
            String arguments = requireArgument(command, "event");
            int fromPosition = arguments.indexOf(" /from ");
            int toPosition = arguments.indexOf(" /to ", fromPosition + 7);
            if (fromPosition <= 0 || toPosition <= fromPosition + 7
                    || toPosition + 5 >= arguments.length()) {
                throw new LarryException();
            }

            String description = arguments.substring(0, fromPosition).trim();
            String startTime = arguments.substring(fromPosition + 7, toPosition).trim();
            String endTime = arguments.substring(toPosition + 5).trim();
            if (description.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
                throw new LarryException();
            }
            try {
                return new Event(description, startTime, endTime);
            } catch (DateTimeParseException e) {
                throw new LarryException();
            }
        }

        throw new LarryException();
    }

    /**
     * Parses and validates a date supplied to a command.
     *
     * @param command Full command containing the date.
     * @param keyword Command keyword preceding the date.
     * @return Parsed date.
     * @throws LarryException If the date is absent or invalid.
     */
    public static LocalDate parseDate(String command, String keyword) throws LarryException {
        String dateText = requireArgument(command, keyword);
        try {
            return TaskDateTime.parseDate(dateText);
        } catch (DateTimeParseException e) {
            throw new LarryException();
        }
    }

    /**
     * Parses and validates a one-based task number supplied to a task command.
     *
     * @param command Full mark, unmark, or delete command.
     * @param keyword Command keyword.
     * @param taskCount Number of tasks currently stored.
     * @return Validated zero-based task index.
     * @throws LarryException If the task number is absent, non-numeric, or out of range.
     */
    public static int parseTaskIndex(String command, String keyword, int taskCount)
            throws LarryException {
        String indexText = requireArgument(command, keyword);
        try {
            int taskIndex = Integer.parseInt(indexText) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
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
        String argument = command.substring(keyword.length()).trim();
        if (argument.isEmpty()) {
            throw new LarryException();
        }
        return argument;
    }
}
