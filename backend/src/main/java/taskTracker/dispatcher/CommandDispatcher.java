package taskTracker.dispatcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;
import taskTracker.model.Task;
import taskTracker.repository.JSONRepository;
import taskTracker.repository.TaskMapContainer;
import taskTracker.service.JSONTaskService;
import taskTracker.service.TaskService;
import taskTracker.utils.CLIPrinter;
import taskTracker.commands.*;
import taskTracker.enums.CommandStrategy;
import taskTracker.utils.ContainerPrinter;
import taskTracker.utils.ListPrinter;

@Component
public class CommandDispatcher {

    private final JSONTaskService service;
    private final CLIPrinter<Task> printer;
    private final JSONRepository jSONRepository;

    public CommandDispatcher(JSONTaskService service, CLIPrinter<Task> printer, JSONRepository jSONRepository) {
        this.service = service;
        service.readTasks();
        this.printer = printer;
        this.jSONRepository = jSONRepository;
    }

    public String dispatch(String commandType, String[] args) {
        AbstractCommand command;

        String line;
        switch (commandType.toLowerCase()) {
            case "add":
                if (args.length < 2) {
                    throw new IllegalArgumentException("Invalid arguments for add command");
                }
                command = new AddCommand(String.join(" ", args));
                break;

            case "update":
                if (args.length < 3) {
                    throw new IllegalArgumentException("Invalid arguments for update command");
                }
                command = new UpdateCommand(String.join(" ", args));
                break;

            case "delete":
                if (args.length < 1) {
                    throw new IllegalArgumentException("Invalid arguments for delete command");
                }
                line = args[0] + " " + args[1];
                command = new DeleteCommand(line);
                break;

            case "mark-in-progress":
                if (args.length < 1) {
                    throw new IllegalArgumentException("Invalid arguments for mark-in-progress command");
                }
                line = args[0] + " " + args[1];
                command = new MarkInProgressCommand(line);
                break;

            case "mark-done":
                if (args.length < 1) {
                    throw new IllegalArgumentException("Invalid arguments for mark-done command");
                }
                line = args[0] + " " + args[1];
                command = new MarkDoneCommand(line);
                break;

            case "list":
                command = new ListAllCommand(CommandStrategy.LIST, printer);
                break;

            default:
                throw new IllegalArgumentException("Unknown command: " + commandType);
        }

        command.execute(service);
        service.saveTasks();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson("Ok message");
    }

    public String dispatchGet(String[] args) {
        TaskMapContainer taskMapContainer = new TaskMapContainer();
        ListPrinter<Task> listPrinter = new ListPrinter<>();
        AbstractListCommand command = switch (args[0].toLowerCase()) {
            case "done" -> new ListDoneCommand(CommandStrategy.LIST_DONE, listPrinter);
            case "todo" -> new ListToDoCommand(CommandStrategy.LIST_TODO, listPrinter);
            case "in-progress" -> new ListInProgressCommand(CommandStrategy.LIST_IN_PROGRESS, listPrinter);
            default -> throw new IllegalArgumentException("Invalid list command");
        };
        System.out.println("Printam..");
        for (Task t: listPrinter.getList())
            System.out.println(t);

        command.execute(service);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(listPrinter.getList());
    }
}
