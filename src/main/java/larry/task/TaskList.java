package larry.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Stores Larry's tasks and provides operations for accessing and changing them.
 */
public class TaskList implements Iterable<Task> {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the specified index.
     *
     * @param taskIndex Zero-based index of the task to delete.
     * @return Deleted task.
     */
    public Task delete(int taskIndex) {
        return tasks.remove(taskIndex);
    }

    /**
     * Returns the task at the specified index.
     *
     * @param taskIndex Zero-based index of the task to return.
     * @return Task at the specified index.
     */
    public Task get(int taskIndex) {
        return tasks.get(taskIndex);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of stored tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Finds tasks whose descriptions contain the keyword, ignoring letter case.
     *
     * @param keyword Keyword to search for.
     * @return Matching tasks in their original list order.
     */
    public List<Task> findByDescription(String keyword) {
        String normalizedKeyword = Objects.requireNonNull(keyword, "keyword")
                .toLowerCase(Locale.ROOT);
        return tasks.stream()
                .filter(task -> task.getDescription().toLowerCase(Locale.ROOT)
                        .contains(normalizedKeyword))
                .toList();
    }

    /**
     * Returns a read-only iterator over the tasks in their list order.
     *
     * @return Iterator over the stored tasks.
     */
    @Override
    public Iterator<Task> iterator() {
        return Collections.unmodifiableList(tasks).iterator();
    }
}
