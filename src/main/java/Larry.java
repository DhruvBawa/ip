import java.util.Scanner;

/**
 * Runs the Larry chatbot application.
 */
public class Larry {
    private static final String SEPARATOR = "    __________________________________________________________";
    private static final int MAX_TASKS = 100;

    /**
     * Starts Larry and responds to commands until the user enters {@code bye}.
     *
     * @param args command-line arguments; not used
     */
    public static void main(String[] args) {
        System.out.println(SEPARATOR + "\n");

        String banner = """
                         ███████╗██╗   ██╗██╗██╗
                         ██╔════╝██║   ██║██║██║
                         █████╗  ██║   ██║██║██║
                         ██╔══╝  ╚██╗ ██╔╝██║██║
                         ███████╗ ╚████╔╝ ██║███████╗
                         ╚══════╝  ╚═══╝  ╚═╝╚══════╝

                 ██╗      █████╗ ██████╗ ██████╗ ██╗   ██╗
                 ██║     ██╔══██╗██╔══██╗██╔══██╗╚██╗ ██╔╝
                 ██║     ███████║██████╔╝██████╔╝ ╚████╔╝
                 ██║     ██╔══██║██╔══██╗██╔══██╗  ╚██╔╝
                 ███████╗██║  ██║██║  ██║██║  ██║   ██║
                 ╚══════╝╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═╝   ╚═╝

        ██████████╗                                             ╔██████████
          ╚██████████╗                                       ╔██████████╝
             ╚██████████╗                                 ╔██████████╝
                ███████████████████████████████████████████████
                 █████████████████████████████████████████████
                  ███████████████████████████████████████████
                  ████████    ░▓██▓░       ░▓██▓░    ████████
                   ███████     ▀██▀         ▀██▀     ███████
                   █████████████████████████████████████████
                    ███████████████████▄█▄█████████████████
                     ██████████████████▀█▀████████████████
                      ███████████████   ▄   █████████████
                       ██████████████  ╲_╱  ████████████
                        ████████████████████████████████
                          ████████████████████████████
                            ████████████████████████
                             ██████████████████████
                            ████████████████████████
                         ██████████████████████████████
                     ██████████████████████████████████████
                 ██████████████████████████████████████████████
                """;
        System.out.println(banner);

        System.out.println("Hello! I'm Larry.\nWhat can I do for you?");
        System.out.println(SEPARATOR);

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println("     EVIL LARRY has decided to let you go\n     FOR NOW...");
                System.out.println(SEPARATOR);
                break;
            }

            try {
                if (command.equals("list")) {
                    System.out.println("     Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("     " + (i + 1) + "." + tasks[i]);
                    }
                } else if (isCommand(command, "mark")) {
                    int taskIndex = parseTaskIndex(command, "mark", taskCount);
                    tasks[taskIndex].markAsDone();
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       " + tasks[taskIndex]);
                } else if (isCommand(command, "unmark")) {
                    int taskIndex = parseTaskIndex(command, "unmark", taskCount);
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       " + tasks[taskIndex]);
                } else {
                    if (taskCount >= MAX_TASKS) {
                        throw new LarryException();
                    }

                    Task newTask = parseTask(command);
                    tasks[taskCount] = newTask;
                    taskCount++;
                    System.out.println("     Got it. I've added this task:");
                    System.out.println("       " + newTask);
                    String taskWord = taskCount == 1 ? "task" : "tasks";
                    System.out.println("     Now you have " + taskCount + " " + taskWord + " in the list.");
                }
            } catch (LarryException e) {
                System.out.println("     " + e.getMessage());
            }

            System.out.println(SEPARATOR);
        }
        scanner.close();
    }

    /**
     * Checks whether an input is a command keyword, optionally followed by arguments.
     *
     * @param input full user input
     * @param keyword command keyword to match
     * @return true when the input invokes the specified command
     */
    private static boolean isCommand(String input, String keyword) {
        return input.equals(keyword) || input.startsWith(keyword + " ");
    }

    /**
     * Converts a task-creation command into the appropriate task subtype.
     *
     * @param command full task-creation command
     * @return task represented by the command
     * @throws LarryException if the command or any required field is invalid
     */
    private static Task parseTask(String command) throws LarryException {
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
            return new Deadline(description, dueDate);
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
            return new Event(description, startTime, endTime);
        }

        throw new LarryException();
    }

    /**
     * Extracts a required command argument and rejects blank values.
     *
     * @param command full user command
     * @param keyword command keyword preceding the argument
     * @return trimmed argument text
     * @throws LarryException if the argument is empty
     */
    private static String requireArgument(String command, String keyword) throws LarryException {
        String argument = command.substring(keyword.length()).trim();
        if (argument.isEmpty()) {
            throw new LarryException();
        }
        return argument;
    }

    /**
     * Parses and validates the one-based task number used by mark commands.
     *
     * @param command full mark or unmark command
     * @param keyword command keyword
     * @param taskCount number of tasks currently stored
     * @return validated zero-based task index
     * @throws LarryException if the task number is absent, non-numeric, or out of range
     */
    private static int parseTaskIndex(String command, String keyword, int taskCount)
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
}
