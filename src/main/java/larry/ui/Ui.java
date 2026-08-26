package larry.ui;

import java.time.LocalDate;
import java.util.Scanner;

import larry.task.Task;
import larry.task.TaskDateTime;
import larry.task.TaskList;

/**
 * Handles console input and presents Larry's responses to the user.
 */
public class Ui implements AutoCloseable {
    private static final String SEPARATOR = "    __________________________________________________________";
    private static final String INIT_SPACE = "     ";
    private static final String PRE_TASK_SPACE = "       ";
    private static final String BANNER = """
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

    private final Scanner scanner;

    /**
     * Creates a console UI that reads from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Checks whether another command is available from the user.
     *
     * @return True when another command can be read.
     */
    public boolean hasNextCommand() {
        return scanner.hasNextLine();
    }

    /**
     * Reads the next command from the user.
     *
     * @return Next command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays Larry's banner and greeting.
     */
    public void showWelcome() {
        System.out.println(SEPARATOR + "\n");
        System.out.println(BANNER);
        System.out.println("I'm EVIL LARRY.\nWhat do you want to do?");
        showSeparator();
    }

    /**
     * Displays a separator between interactions.
     */
    public void showSeparator() {
        System.out.println(SEPARATOR);
    }

    /**
     * Displays Larry's farewell message.
     */
    public void showGoodbye() {
        System.out.println(INIT_SPACE + "EVIL LARRY has decided to let you go\n     FOR NOW...");
    }

    /**
     * Displays every task with its one-based task number.
     *
     * @param tasks Tasks to display.
     */
    public void showTasks(TaskList tasks) {
        System.out.println(INIT_SPACE + "Here are the tasks EVIL LARRY says are in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("     " + (i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Displays deadlines and events occurring on a requested date.
     *
     * @param date Date whose tasks should be displayed.
     * @param tasks Tasks to search and display.
     */
    public void showTasksOnDate(LocalDate date, TaskList tasks) {
        System.out.println(INIT_SPACE + "EVIL LARRY says these tasks occur on "
                + TaskDateTime.formatDate(date) + ":");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.occursOn(date)) {
                System.out.println("     " + (i + 1) + "." + task);
            }
        }
    }

    /**
     * Displays confirmation that a task was marked as done.
     *
     * @param task Task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println(INIT_SPACE + "EVIL LARRY has marked this task as done:");
        System.out.println(PRE_TASK_SPACE + task);
    }

    /**
     * Displays confirmation that a task was marked as not done.
     *
     * @param task Task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println(INIT_SPACE + "EVIL LARRY has marked this task as not done yet:");
        System.out.println(PRE_TASK_SPACE + task);
    }

    /**
     * Displays confirmation that a task was deleted and the new task count.
     *
     * @param task Task that was deleted.
     * @param taskCount Number of remaining tasks.
     */
    public void showTaskDeleted(Task task, int taskCount) {
        System.out.println(INIT_SPACE + "EVIL LARRY removed this task:");
        System.out.println(PRE_TASK_SPACE + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays confirmation that a task was added and the new task count.
     *
     * @param task Task that was added.
     * @param taskCount Number of stored tasks.
     */
    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(INIT_SPACE + "EVIL LARRY has added this task for you:");
        System.out.println(PRE_TASK_SPACE + task);
        showTaskCount(taskCount);
    }

    /**
     * Displays an invalid-command error.
     *
     * @param message Error message to display.
     */
    public void showError(String message) {
        System.out.println(INIT_SPACE + message);
    }

    /**
     * Displays a non-fatal warning.
     *
     * @param message Warning message to display.
     */
    public void showWarning(String message) {
        System.err.println(message);
    }

    /**
     * Warns that Larry could not load the task data file.
     *
     * @param errorMessage Cause of the loading failure.
     */
    public void showLoadingError(String errorMessage) {
        System.err.println("WARNING: EVIL LARRY could not read the task data file. "
                + "Starting with an empty task list. " + errorMessage);
    }

    /**
     * Warns that Larry could not save the latest task change.
     *
     * @param errorMessage Cause of the saving failure.
     */
    public void showSavingError(String errorMessage) {
        System.err.println("WARNING: EVIL LARRY could not save the task data file. "
                + "The latest change is available only in this session. " + errorMessage);
    }

    /**
     * Closes the console input reader.
     */
    @Override
    public void close() {
        scanner.close();
    }

    /**
     * Displays the number of tasks currently stored.
     *
     * @param taskCount Number of stored tasks.
     */
    private void showTaskCount(int taskCount) {
        String taskWord = taskCount == 1 ? "task" : "tasks";
        System.out.println(INIT_SPACE + "EVIL LARRY says you have " + taskCount + " "
                + taskWord + " in the list.");
    }
}
