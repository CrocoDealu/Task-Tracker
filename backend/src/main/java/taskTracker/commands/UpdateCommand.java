package taskTracker.commands;

import taskTracker.service.TaskService;
import taskTracker.utils.ComandParser;

import taskTracker.enums.CommandStrategy;

public class UpdateCommand extends AbstractCommand{

    private final String[] stringTask;
    public UpdateCommand(String args) {
        super(args);
        stringTask = ComandParser.getInstance().parse(args, CommandStrategy.UPDATE);
    }

    @Override
    public void execute(TaskService o) {
        int tid = Integer.parseInt(stringTask[0]);
        String taskS = stringTask[1];
        String taskD = stringTask[2];
        o.updateTask(tid, taskS, taskD);
    }
}
