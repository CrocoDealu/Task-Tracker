package taskTracker.commands;

import taskTracker.enums.CommandStrategy;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.Printer;

import java.util.List;

public class ListInProgressCommand extends AbstractListCommand{
    public ListInProgressCommand(CommandStrategy c, Printer<Task> printer) {
        super(c, printer);
    }

    @Override
    public void execute(TaskService o) {
        List<Task> l = o.filterByStatus(o, "in-progress");
        printer.printAll(l);
    }
}
