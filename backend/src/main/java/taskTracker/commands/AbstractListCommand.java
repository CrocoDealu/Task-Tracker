package taskTracker.commands;

import taskTracker.enums.CommandStrategy;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.Printer;

public abstract class AbstractListCommand extends AbstractCommand{
    protected final Printer<Task> printer;
    CommandStrategy commandStrategy;
    public AbstractListCommand(CommandStrategy c, Printer<Task> printer) {
        super("");
        this.printer = printer;
        commandStrategy = c;
    }
}
