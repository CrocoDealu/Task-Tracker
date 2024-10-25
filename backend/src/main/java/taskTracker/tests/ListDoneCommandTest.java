package taskTracker.tests;

import org.junit.jupiter.api.Test;
import taskTracker.commands.ListDoneCommand;
import taskTracker.repository.JSONRepository;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.ContainerPrinter;
import taskTracker.enums.CommandStrategy;

public class ListDoneCommandTest {
    @Test
    public void testExecute() {
        JSONRepository container = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        TaskService service = new TaskService(container);
        ContainerPrinter<Task> printer = new ContainerPrinter<>(container);

        ListDoneCommand c = new ListDoneCommand(CommandStrategy.LIST_DONE, printer);
        c.execute(service);
        assert printer.getContainer().isEmpty();

        Task t1 = new Task(1,"title1", "ss", "done");

        container.add(t1);

        c.execute(service);
        assert printer.getContainer().size() == 1;
    }
}
