package taskTracker.repository;

import taskTracker.exceptions.IdNotFoundException;
import taskTracker.model.Task;

import java.util.HashMap;

public class TaskMapContainer extends AbstractMapContainer<Task> {

    private static final int NOT_INITIALIZED = 0;
    private int lastId;

    public TaskMapContainer() {
        lastId = 1;
    }

    @Override
    public Task remove(int taskId) {
        Task task = elems.remove(taskId);
        if (task == null) {
            throw new IdNotFoundException("Task with ID " + taskId + " not found.");
        }
        return task;
    }

    @Override
    public void add(Task task) {
        if (task.getId() == NOT_INITIALIZED) {
            task.setId(generateNextId());
        }
        elems.put(task.getId(), task);
        if (task.getId() >= lastId) {
            lastId = task.getId() + 1;
        }
    }

    private int generateNextId() {
        return lastId++;
    }

    public void modify(int taskId, Task updatedTask) {
        Task task = elems.get(taskId);
        if (task != null) {
            elems.replace(taskId, updatedTask);
        } else {
            throw new IdNotFoundException("Task with ID " + taskId + " not found.");
        }
    }

    public Task get(int taskId) {
        Task task = elems.get(taskId);
        if (task != null) {
            return task;
        }
        throw new IdNotFoundException("Task with ID " + taskId + " not found.");
    }

    public void setTasks(HashMap<Integer, Task> tasks) {
        this.elems = tasks;
    }
}
