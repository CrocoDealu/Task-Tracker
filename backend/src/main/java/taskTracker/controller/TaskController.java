package taskTracker.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import taskTracker.dispatcher.CommandDispatcher;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final CommandDispatcher commandDispatcher;

    @Autowired
    public TaskController(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    // POST /api/tasks - Add a new task
    @PostMapping
    public ResponseEntity<String> addTask(@RequestParam String title, @RequestParam String description) {
        try {
            title = '"' + title + '"';
            description = '"' + description + '"';
            String[] args = {title, description};
            String result = commandDispatcher.dispatch("add", args);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/tasks/{id} - Update a task
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable int id, @RequestParam String title, @RequestParam String description) {
        try {
            String[] args = {"update", String.valueOf(id), title, description};
            String result = commandDispatcher.dispatch("update", args);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/tasks/{id} - Delete a task
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        try {

            String[] args = {"delete", String.valueOf(id)};

            String result = commandDispatcher.dispatch("delete", args);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PATCH /api/tasks/{id}/status - Mark task as done or in-progress
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateTaskStatus(@PathVariable int id, @RequestParam String status) {
        try {
            String[] args = {"", String.valueOf(id)};
            String result;
            if ("done".equalsIgnoreCase(status)) {
                args[0] = "mark-done";
                result = commandDispatcher.dispatch("mark-done", args);
            } else if ("in-progress".equalsIgnoreCase(status)) {
                args[0] = "mark-in-progress";
                result = commandDispatcher.dispatch("mark-in-progress", args);
            } else {
                throw new IllegalArgumentException("Invalid status: " + status);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/tasks - List all tasks
    @GetMapping
    public ResponseEntity<String> listTasks(@RequestParam(required = false) String status) {
        try {
            String result;
            System.out.println("plm");
            if (status == null) {
                result = commandDispatcher.dispatch("list", new String[]{});
            } else {
                result = commandDispatcher.dispatch("list", new String[]{status});
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/tasks/done - List done tasks
    @GetMapping("/done")
    public ResponseEntity<String> listDoneTasks() {
        try {
            String result = commandDispatcher.dispatchGet(new String[]{"done"});
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/tasks/todo - List todo tasks
    @GetMapping("/todo")
    public ResponseEntity<String> listTodoTasks() {
        try {
            String result = commandDispatcher.dispatchGet(new String[]{"todo"});
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/tasks/in-progress - List in-progress tasks
    @GetMapping("/in-progress")
    public ResponseEntity<String> listInProgressTasks() {
        try {
            String result = commandDispatcher.dispatchGet(new String[]{"in-progress"});
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

