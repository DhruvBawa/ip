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
   * Creates storage that writes to the given file path.
   *
   * @param filePath path of the task data file
   */
  public Storage(Path filePath) {
    this.filePath = filePath;
  }

  /**
   * Replaces the data file with the current task list.
   *
   * @param tasks current tasks to save
   * @throws IOException if the directory or data file cannot be written
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
   * Converts a task to the compact line format stored in the data file.
   *
   * @param task task to serialize
   * @return one line containing the task type, status, and details
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
