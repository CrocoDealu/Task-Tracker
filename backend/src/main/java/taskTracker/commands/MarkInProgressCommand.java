package taskTracker.commands;

import taskTracker.enums.CommandStrategy;
import taskTracker.service.TaskService;
import taskTracker.utils.ComandParser;

public class MarkInProgressCommand extends AbstractCommand{

    private String[] stringTask;
    public MarkInProgressCommand(String args) {
        super(args);
        CommandStrategy strategy = CommandStrategy.MARK_IN_PROGRESS;
        stringTask = ComandParser.getInstance().parse(args, strategy);
    }

    @Override
    public void execute(TaskService o) {
        int tid = Integer.parseInt(stringTask[0]);
        o.markTaskInProgress(tid);
    }
}
