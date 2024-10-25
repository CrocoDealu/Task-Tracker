package taskTracker.tests;

import org.junit.jupiter.api.Test;
import taskTracker.commands.MarkInProgressCommand;
import taskTracker.repository.JSONRepository;
import taskTracker.model.Task;
import taskTracker.service.TaskService;

import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;

public class MarkInProgressCommandTest {
    @Test
    public void testExecute() {
        JSONRepository container = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        TaskService service = new TaskService(container);
        Task task = new Task(0, "title1", "Task", "to do", LocalDateTime.now(), LocalDateTime.now());
        container.add(task);

        MarkInProgressCommand markInProgressCommand = new MarkInProgressCommand("mark_in_progress 1");
        markInProgressCommand.execute(service);

        Task modifiedTask = container.get(1);
        assert modifiedTask.getStatus().equals("in progress");
    }

    @Test
    public void testExecuteInvalidInput() {
        assertThrows(RuntimeException.class, () -> {
            new MarkInProgressCommand("mark-in-progress InvalidInput");
        });
    }
}
