package taskTracker.commands;

import taskTracker.enums.CommandStrategy;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.ComandParser;

import java.time.LocalDateTime;

public class AddCommand extends AbstractCommand{

    private final String[] stringTask;
    public AddCommand(String args) {
        super(args);
        CommandStrategy commandStrategy = CommandStrategy.ADD;
        stringTask = ComandParser.getInstance().parse(args, commandStrategy);
    }

    @Override
    public void execute(TaskService o) {
        Task task = new Task(0, stringTask[0], stringTask[1], "to do", LocalDateTime.now(), LocalDateTime.now());
        o.addTask(task);
    }
}
