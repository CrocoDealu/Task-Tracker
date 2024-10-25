package taskTracker.commands;

public interface Command<T> {
    public void execute(T o);
}
