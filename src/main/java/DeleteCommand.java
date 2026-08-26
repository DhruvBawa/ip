/**
 * Deletes a specified task from the task list.
 */
public class DeleteCommand extends Command {
    private final int taskIndex;

    /**
     * Creates a command that deletes the task at the specified index.
     *
     * @param taskIndex Zero-based index of the task to delete.
     */
    public DeleteCommand(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws LarryException {
        validateTaskIndex(tasks, taskIndex);
        Task removedTask = tasks.delete(taskIndex);
        saveTasks(tasks, ui, storage);
        ui.showTaskDeleted(removedTask, tasks.size());
    }
}
