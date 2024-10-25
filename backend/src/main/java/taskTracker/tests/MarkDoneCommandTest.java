package taskTracker.tests;

import org.junit.jupiter.api.Test;
import taskTracker.commands.MarkDoneCommand;
import taskTracker.model.Task;
import taskTracker.repository.JSONRepository;
import taskTracker.service.TaskService;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;

public class MarkDoneCommandTest {
    @Test
    public void testExecute() {
        JSONRepository container = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        TaskService service = new TaskService(container);
        Task task = new Task(0,"title1", "Task", "to do", LocalDateTime.now(), LocalDateTime.now());
        container.add(task);

        MarkDoneCommand markDoneCommand = new MarkDoneCommand("mark_done 1");
        markDoneCommand.execute(service);

        Task modifiedTask = container.get(1);
        assert modifiedTask.getStatus().equals("done");
    }

    @Test
    public void testExecuteInvalidInput() throws IOException {
        assertThrows(RuntimeException.class, () -> {
            new MarkDoneCommand("mark-done InvalidInput");
        });
    }
}

