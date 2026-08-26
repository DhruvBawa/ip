import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Saves Larry's task list to a file on disk.
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates storage that reads from and writes to the given file path.
     *
     * @param filePath Path of the task data file.
     */
    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the data file, or returns an empty list if it does not exist.
     *
     * @return Tasks stored in the data file.
     * @throws IOException If the data file cannot be read.
     */
    public ArrayList<Task> loadTasks() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
            tasks.add(deserialize(line));
        }
        return tasks;
    }

    /**
     * Replaces the data file with the current task list.
     *
     * @param tasks Current tasks to save.
     * @throws IOException If the directory or data file cannot be written.
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        Path parentDirectory = filePath.getParent();
        if (parentDirectory != null) {
            Files.createDirectories(parentDirectory);
        }

        ArrayList<String> taskLines = new ArrayList<>();
        for (Task task : tasks) {
            taskLines.add(serialize(task));
        }
        Files.write(filePath, taskLines, StandardCharsets.UTF_8);
    }

    /**
     * Converts a data-file line to its corresponding task.
     *
     * @param line Serialized task data.
     * @return Task represented by the data-file line.
     */
    private Task deserialize(String line) {
        String[] parts = line.split(" \\| ");
        Task task = switch (parts[0]) {
            case "T" -> new Todo(parts[2]);
            case "D" -> new Deadline(parts[2], parts[3]);
            case "E" -> new Event(parts[2], parts[3], parts[4]);
            default -> throw new IllegalArgumentException("Unknown task type: " + parts[0]);
        };
        if (parts[1].equals("1")) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Converts a task to the compact line format stored in the data file.
     *
     * @param task Task to serialize.
     * @return One line containing the task type, status, and details.
     */
    private String serialize(Task task) {
        String status = task.isDone ? "1" : "0";
        if (task instanceof Deadline deadline) {
            return "D | " + status + " | " + task.description + " | " + deadline.dueDate;
        }
        if (task instanceof Event event) {
            return "E | " + status + " | " + task.description + " | " + event.startTime
                    + " | " + event.endTime;
        }
        return "T | " + status + " | " + task.description;
    }
}
