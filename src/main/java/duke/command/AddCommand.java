package duke.command;

import duke.storage.Storage;
import duke.TaskList;
import duke.models.Task;
import duke.ui.Ui;

/**
 * Adds new Task to the TaskList
 */
public class AddCommand extends Command {
    private final Task toAdd;

    public AddCommand(Task task) {
        this.toAdd = task;
    }


    @Override
    public void execute(TaskList tasks, Storage storage, Ui ui) {
        tasks.addTask(toAdd);
        storage.write(toAdd.stringToWrite());
        ui.newItemAdded(toAdd, tasks.getSize());
    }
}
