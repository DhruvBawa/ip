import java.io.IOException;
import java.nio.file.Path;

/**
 * Runs the Larry chatbot application.
 */
public class Larry {
    private static final Path DATA_FILE_PATH = Path.of("data", "larry.txt");

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Creates Larry with storage at the specified path and loads the saved tasks.
     *
     * @param dataFilePath Path of the task data file.
     */
    public Larry(Path dataFilePath) {
        this.ui = new Ui();
        this.storage = new Storage(dataFilePath);
        this.tasks = loadTasks();
    }

    /**
     * Creates and runs Larry using the default task data path.
     *
     * @param args Command-line arguments; not used.
     */
    public static void main(String[] args) {
        new Larry(DATA_FILE_PATH).run();
    }

    /**
     * Responds to commands until the user enters {@code bye} or closes the input.
     */
    public void run() {
        try {
            ui.showWelcome();
            while (ui.hasNextCommand()) {
                String fullCommand = ui.readCommand();
                ui.showSeparator();

                if (fullCommand.equals("bye")) {
                    ui.showGoodbye();
                    ui.showSeparator();
                    break;
                }

                try {
                    executeCommand(fullCommand);
                } catch (LarryException e) {
                    ui.showError(e.getMessage());
                } finally {
                    ui.showSeparator();
                }
            }
        } finally {
            ui.close();
        }
    }

    /**
     * Executes a non-exit command.
     *
     * @param fullCommand Full user command.
     * @throws LarryException If the command or its arguments are invalid.
     */
    private void executeCommand(String fullCommand) throws LarryException {
        if (fullCommand.equals("list")) {
            ui.showTasks(tasks);
        } else if (Parser.isCommand(fullCommand, "on")) {
            ui.showTasksOnDate(Parser.parseDate(fullCommand, "on"), tasks);
        } else if (Parser.isCommand(fullCommand, "mark")) {
            int taskIndex = Parser.parseTaskIndex(fullCommand, "mark", tasks.size());
            Task task = tasks.get(taskIndex);
            task.markAsDone();
            saveTasks();
            ui.showTaskMarked(task);
        } else if (Parser.isCommand(fullCommand, "unmark")) {
            int taskIndex = Parser.parseTaskIndex(fullCommand, "unmark", tasks.size());
            Task task = tasks.get(taskIndex);
            task.markAsNotDone();
            saveTasks();
            ui.showTaskUnmarked(task);
        } else if (Parser.isCommand(fullCommand, "delete")) {
            int taskIndex = Parser.parseTaskIndex(fullCommand, "delete", tasks.size());
            Task removedTask = tasks.delete(taskIndex);
            saveTasks();
            ui.showTaskDeleted(removedTask, tasks.size());
        } else {
            Task newTask = Parser.parseTask(fullCommand);
            tasks.add(newTask);
            saveTasks();
            ui.showTaskAdded(newTask, tasks.size());
        }
    }

    /**
     * Loads saved tasks without preventing Larry from starting after a read failure.
     *
     * @return Loaded tasks, or an empty task list when the data file cannot be read.
     */
    private TaskList loadTasks() {
        try {
            TaskList loadedTasks = storage.loadTasks();
            for (String warning : storage.getLoadWarnings()) {
                ui.showWarning(warning);
            }
            return loadedTasks;
        } catch (IOException | SecurityException e) {
            ui.showLoadingError(e.getMessage());
            return new TaskList();
        }
    }

    /**
     * Saves tasks and warns the user if the latest change cannot be persisted.
     */
    private void saveTasks() {
        try {
            storage.saveTasks(tasks);
        } catch (IOException | SecurityException e) {
            ui.showSavingError(e.getMessage());
        }
    }
}
