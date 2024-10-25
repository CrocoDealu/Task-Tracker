package taskTracker.tests;

import org.junit.jupiter.api.Test;
import taskTracker.commands.DeleteCommand;
import taskTracker.model.Task;
import taskTracker.repository.JSONRepository;
import taskTracker.service.TaskService;

import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;

public class DeleteCommandTest {
    @Test
    public void testExecute() {
        JSONRepository container = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        TaskService service = new TaskService(container);
        Task task = new Task(0,"title1", "Delete this task", "to do", LocalDateTime.now(), LocalDateTime.now());
        container.add(task);

        DeleteCommand deleteCommand = new DeleteCommand("delete 1");
        deleteCommand.execute(service);
        assert container.isEmpty();
    }

    @Test
    public void testExecuteInvalidInput() {
        assertThrows(RuntimeException.class, () -> {
            new DeleteCommand("delete InvalidInput");
        });
    }
}

