package taskTracker.tests;

import org.junit.jupiter.api.Test;
import taskTracker.commands.AddCommand;
import taskTracker.repository.JSONRepository;
import taskTracker.model.Task;
import taskTracker.service.TaskService;


import static org.junit.Assert.assertThrows;

public class AddCommandTest {
    @Test
    public void testExecute() {
        JSONRepository container = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        TaskService service = new TaskService(container);
        AddCommand addCommand = null;
        try {
             addCommand = new AddCommand("add \"Title1\" \"New Task\"");
        } catch (Exception e) {
            assert false;
        }
        addCommand.execute(service);
        assert container.size() == 1;

        Task addedTask = container.get(1);
        assert addedTask.getDescription().equals("New Task");
        assert addedTask.getStatus().equals("to do");
    }

    @Test
    public void testExecuteInvalidInput() {
        assertThrows(RuntimeException.class, () -> {
            new AddCommand("add InvalidInput");
        });
    }
}

