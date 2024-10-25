package taskTracker.tests;

import org.junit.jupiter.api.Test;
import taskTracker.commands.UpdateCommand;
import taskTracker.model.Task;
import taskTracker.repository.JSONRepository;
import taskTracker.service.TaskService;

import java.time.LocalDateTime;

import static org.junit.Assert.assertThrows;

public class UpdateCommandTest {
    @Test
    public void testExecute() {
        JSONRepository container = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        TaskService service = new TaskService(container);
        Task task = new Task(1,"title1", "Old Task", "to do", LocalDateTime.now(), LocalDateTime.now());
        container.add(task);

        UpdateCommand updateCommand = new UpdateCommand("update 1 \"Title\" \"Updated Task\"");
        updateCommand.execute(service);

        Task updatedTask = container.get(1);
        assert updatedTask.getDescription().equals("Updated Task");
        assert updatedTask.getStatus().equals("to do");
    }

    @Test
    public void testExecuteInvalidInput() {
        assertThrows(RuntimeException.class, () -> {
            new UpdateCommand("update InvalidInput");
        });
    }
}
