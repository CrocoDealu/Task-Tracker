package taskTracker.tests;

import org.junit.jupiter.api.Test;
import taskTracker.commands.ListInProgressCommand;
import taskTracker.repository.JSONRepository;
import taskTracker.model.Task;
import taskTracker.service.TaskService;
import taskTracker.utils.ContainerPrinter;
import taskTracker.enums.CommandStrategy;

public class ListInProgressCommandTest {
    @Test
    public void testExecute() {
        JSONRepository container = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        JSONRepository printingContainer = new JSONRepository("src/main/java/taskTracker/testTasks.json");
        ContainerPrinter<Task> printer = new ContainerPrinter<>(container);
        ContainerPrinter<Task> printer2 = new ContainerPrinter<>(printingContainer);


        TaskService service = new TaskService(container);
        TaskService pservice = new TaskService(printingContainer);
        ListInProgressCommand c = new ListInProgressCommand(CommandStrategy.LIST_IN_PROGRESS, printer);
        c.execute(service);
        assert printer.getContainer().isEmpty();

        Task t1 = new Task(1,"title1", "ss", "done");

        container.add(t1);

        c.execute(pservice);
        assert printer2.getContainer().isEmpty();

        Task t2 = new Task(1,"title1", "ss", "in progress");
        container.add(t2);

        c.execute(pservice);
        assert printer.getContainer().size() == 1;

    }

}
