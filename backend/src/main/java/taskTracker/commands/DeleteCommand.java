package taskTracker.commands;

import taskTracker.service.TaskService;
import taskTracker.utils.ComandParser;

import taskTracker.enums.CommandStrategy;


public class DeleteCommand extends AbstractCommand{

    private final String[] stringTask;
    public DeleteCommand(String args){
        super(args);
        CommandStrategy commandStrategy = CommandStrategy.DELETE;
        stringTask = ComandParser.getInstance().parse(args, commandStrategy);
    }

    @Override
    public void execute(TaskService o) {
        int tid = Integer.parseInt(stringTask[0]);
        o.deleteTask(tid);
    }
}
