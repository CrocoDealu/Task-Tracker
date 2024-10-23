package taskTracker.dispatcher;

import org.springframework.stereotype.Component;
import taskTracker.model.Task;
import taskTracker.service.JSONTaskService;
import taskTracker.utils.CLIPrinter;
import taskTracker.commands.*;
import taskTracker.enums.CommandStrategy;

@Component
public class CommandDispatcher {

    private final JSONTaskService service;
    private final CLIPrinter<Task> printer;

    public CommandDispatcher(JSONTaskService service, CLIPrinter<Task> printer) {
        this.service = service;
        service.readTasks();
        this.printer = printer;
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
                if (args.length == 0) {
                    command = new ListAllCommand(CommandStrategy.LIST, printer);
                } else {
                    command = switch (args[0].toLowerCase()) {
                        case "done" -> new ListDoneCommand(CommandStrategy.LIST_DONE, printer);
                        case "todo" -> new ListToDoCommand(CommandStrategy.LIST_TODO, printer);
                        case "in-progress" -> new ListInProgressCommand(CommandStrategy.LIST_IN_PROGRESS, printer);
                        default -> throw new IllegalArgumentException("Invalid list command");
                    };
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown command: " + commandType);
        }

        command.execute(service);
        service.saveTasks();
        return "Command executed successfully";
    }
}
