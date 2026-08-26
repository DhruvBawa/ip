import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Runs the Larry chatbot application.
 */
public class Larry {
    private static final String SEPARATOR = "    __________________________________________________________";
    private static final String INIT_SPACE = "     ";
    private static final String PRE_TASK_SPACE = "       ";
    private static final Path DATA_FILE_PATH = Path.of("data", "larry.txt");

    /**
     * Starts Larry and responds to commands until the user enters {@code bye}.
     *
     * @param args Command-line arguments; not used.
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

        System.out.println("I'm EVIL LARRY.\nWhat do you want to do?");
        System.out.println(SEPARATOR);

        Storage storage = new Storage(DATA_FILE_PATH);
        ArrayList<Task> tasks = loadTasks(storage);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(SEPARATOR);

            if (command.equals("bye")) {
                System.out.println(INIT_SPACE + "EVIL LARRY has decided to let you go\n     FOR NOW...");
                System.out.println(SEPARATOR);
                break;
            }

            try {
                if (command.equals("list")) {
                    System.out.println(INIT_SPACE
                            + "Here are the tasks EVIL LARRY says are in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("     " + (i + 1) + "." + tasks.get(i));
                    }
                } else if (isCommand(command, "mark")) {
                    int taskIndex = parseTaskIndex(command, "mark", tasks.size());
                    Task task = tasks.get(taskIndex);
                    task.markAsDone();
                    saveTasks(storage, tasks);
                    System.out.println(INIT_SPACE + "EVIL LARRY has marked this task as done:");
                    System.out.println(PRE_TASK_SPACE + task);
                } else if (isCommand(command, "unmark")) {
                    int taskIndex = parseTaskIndex(command, "unmark", tasks.size());
                    Task task = tasks.get(taskIndex);
                    task.markAsNotDone();
                    saveTasks(storage, tasks);
                    System.out.println(INIT_SPACE
                            + "EVIL LARRY has marked this task as not done yet:");
                    System.out.println(PRE_TASK_SPACE + task);
                } else if (isCommand(command, "delete")) {
                    int taskIndex = parseTaskIndex(command, "delete", tasks.size());
                    Task removedTask = tasks.remove(taskIndex);
                    saveTasks(storage, tasks);
                    System.out.println(INIT_SPACE + "EVIL LARRY removed this task:");
                    System.out.println(PRE_TASK_SPACE + removedTask);
                    String taskWord = tasks.size() == 1 ? "task" : "tasks";
                    System.out.println(INIT_SPACE + "EVIL LARRY says you have " + tasks.size() + " "
                            + taskWord + " in the list.");
                } else {
                    Task newTask = parseTask(command);
                    tasks.add(newTask);
                    saveTasks(storage, tasks);
                    System.out.println(INIT_SPACE + "EVIL LARRY has added this task for you:");
                    System.out.println(PRE_TASK_SPACE + newTask);
                    String taskWord = tasks.size() == 1 ? "task" : "tasks";
                    System.out.println(INIT_SPACE + "EVIL LARRY says you have " + tasks.size() + " "
                            + taskWord + " in the list.");
                }
            } catch (LarryException e) {
                System.out.println(INIT_SPACE + e.getMessage());
            }

            System.out.println(SEPARATOR);
        }
        scanner.close();
    }

    /**
     * Loads saved tasks without preventing Larry from starting after a read failure.
     *
     * @param storage Storage containing the task data.
     * @return Loaded tasks, or an empty list when the data file cannot be read.
     */
    private static ArrayList<Task> loadTasks(Storage storage) {
        try {
            return storage.loadTasks();
        } catch (IOException | SecurityException e) {
            System.err.println("WARNING: EVIL LARRY could not read the task data file. "
                    + "Starting with an empty task list. " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Saves tasks and warns the user if the latest change cannot be persisted.
     *
     * @param storage Storage receiving the task data.
     * @param tasks Current tasks to save.
     */
    private static void saveTasks(Storage storage, ArrayList<Task> tasks) {
        try {
            storage.saveTasks(tasks);
        } catch (IOException | SecurityException e) {
            System.err.println("WARNING: EVIL LARRY could not save the task data file. "
                    + "The latest change is available only in this session. " + e.getMessage());
        }
    }

    /**
     * Checks whether an input is a command keyword, optionally followed by
     * arguments.
     *
     * @param input Full user input.
     * @param keyword Command keyword to match.
     * @return True when the input invokes the specified command.
     */
    private static boolean isCommand(String input, String keyword) {
        return input.equals(keyword) || input.startsWith(keyword + " ");
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

    /**
     * Parses and validates a one-based task number supplied to a task command.
     *
     * @param command Full mark, unmark, or delete command.
     * @param keyword Command keyword.
     * @param taskCount Number of tasks currently stored.
     * @return Validated zero-based task index.
     * @throws LarryException If the task number is absent, non-numeric, or out of
     *         range.
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
