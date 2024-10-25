package taskTracker.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private int id;
    private String title;
    private String description;
    private String status;
    private String createdAt;
    private String updatedAt;

    public Task(int id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        createdAt = LocalDateTime.now().toString();
        updatedAt = LocalDateTime.now().toString();
    }

    public Task(int id, String title, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt.toString();
        this.updatedAt = updatedAt.toString();
    }

    public Task(int id, String taskS, String taskD, String status, String createdAt, LocalDateTime now) {
        this.id = id;
        this.title = taskS;
        this.description = taskD;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = now.toString();
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {this.createdAt = createdAt.toString();}

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt.toString();
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) && Objects.equals(description, task.description);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title = " + title +
                ", description = " + description +
                ", status = " + status +
                ", createdAt = " + createdAt +
                ", updatedAt = " + updatedAt +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
