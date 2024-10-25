package taskTracker.tests;

import org.junit.jupiter.api.Test;
import taskTracker.commands.ListToDoCommand;
import taskTracker.repository.JSONRepository;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.ContainerPrinter;
import taskTracker.enums.CommandStrategy;

public class ListToDoCommandTest {
    @Test
    public void testExecute() {
        JSONRepository container = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        TaskService service = new TaskService(container);
        ContainerPrinter<Task> printer = new ContainerPrinter<>(container);

        ListToDoCommand c = new ListToDoCommand(CommandStrategy.LIST_TODO, printer);
        c.execute(service);
        assert printer.getContainer().isEmpty();

        Task t1 = new Task(1,"title1", "ss", "done");

        container.add(t1);

        JSONRepository container2 = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        TaskService service1 = new TaskService(container2);
        c.execute(service1);
        assert service1.getAllTasks().isEmpty();

        Task t2 = new Task(1,"title1", "ss", "to do");
        container.add(t2);
        c.execute(service1);
        assert printer.getContainer().size() == 1;
    }
}
