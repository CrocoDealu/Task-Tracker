package taskTracker.commands;

import taskTracker.enums.CommandStrategy;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.Printer;

import java.util.List;

public class ListToDoCommand extends AbstractListCommand{
    public ListToDoCommand(CommandStrategy c, Printer<Task> printer) {
        super(c, printer);
    }

    @Override
    public void execute(TaskService o) {
        List<Task> l = o.filterByStatus(o, "to do");
        printer.printAll(l);
    }
}
