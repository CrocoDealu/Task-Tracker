package taskTracker.service;


import taskTracker.model.Task;
import taskTracker.repository.JSONRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {

    protected final JSONRepository repository;

    public TaskService(JSONRepository repository) {
        this.repository = repository;
    }

    public void addTask(Task task) {
        repository.add(task);
    }

    public void updateTask(int taskId, String newTitle, String newDescription) {
        Task task = repository.get(taskId);
        if (task != null) {
            task.setTitle(newTitle);
            task.setDescription(newDescription);
            task.setUpdatedAt(LocalDateTime.now());
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public void markTaskAsDone(int taskId) {
        Task task = repository.get(taskId);
        if (task != null) {
            task.setStatus("done");
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public void deleteTask(int taskId) {
        Task task = repository.get(taskId);
        if (task != null) {
            repository.remove(taskId);
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public void markTaskInProgress(int taskId) {
        Task task = repository.get(taskId);
        if (task != null) {
            task.setStatus("in-progress");
        } else {
            throw new RuntimeException("Task not found");
        }
    }

    public List<Task> getAllTasks() {
        return repository.getAll();
    }

    public List<Task> filterByStatus(TaskService o, String s) {
        return o.getAllTasks().stream()
                .filter(task -> task.getStatus().equals(s))
                .collect(Collectors.toList());
    }
}
