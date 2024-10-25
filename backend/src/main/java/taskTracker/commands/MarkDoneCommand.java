package taskTracker.commands;

import taskTracker.service.TaskService;
import taskTracker.utils.ComandParser;
import taskTracker.enums.CommandStrategy;

public class MarkDoneCommand extends AbstractCommand{

    private String[] stringTask;
    public MarkDoneCommand(String args) {
        super(args);
        CommandStrategy strategy = CommandStrategy.MARK_DONE;
        stringTask = ComandParser.getInstance().parse(args, strategy);
    }

    @Override
    public void execute(TaskService o) {
        int tid = Integer.parseInt(stringTask[0]);
        o.markTaskAsDone(tid);
    }
}
