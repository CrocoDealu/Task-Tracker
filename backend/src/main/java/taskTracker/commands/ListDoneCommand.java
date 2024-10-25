package taskTracker.commands;

import taskTracker.enums.CommandStrategy;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.Printer;

import java.util.List;

public class ListDoneCommand extends AbstractListCommand{
    public ListDoneCommand(CommandStrategy c, Printer<Task> printer) {
        super(c, printer);
    }

    @Override
    public void execute(TaskService o) {
        List<Task> filtered = o.filterByStatus(o, "done");
        printer.printAll(filtered);
    }
}
