package taskTracker.commands;

import taskTracker.service.TaskService;

public abstract class AbstractCommand implements Command<TaskService> {
    protected String args;
    public AbstractCommand(String args) {
        this.args = args;
    }
}
