/**
 * Marks a specified task as done.
 */
public class MarkCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that marks the task at the specified index.
     *
     * @param taskIndex Zero-based index of the task to mark.
     */
    public MarkCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LarryException {
        validateTaskIndex(tasks, taskIndex);
        Task task = tasks.get(taskIndex);
        task.markAsDone();
        saveTasks(tasks, ui, storage);
        ui.showTaskMarked(task);
    }
}
