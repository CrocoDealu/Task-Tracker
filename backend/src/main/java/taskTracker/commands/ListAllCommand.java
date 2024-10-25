package taskTracker.commands;

import taskTracker.enums.CommandStrategy;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.Printer;

import java.util.List;

public class ListAllCommand extends AbstractListCommand {
    public ListAllCommand(CommandStrategy c, Printer<Task> printer) {
        super(c, printer);
    }

    @Override
    public void execute(TaskService o) {
        List<Task> l = o.getAllTasks();
        printer.printAll(l);
    }
}
