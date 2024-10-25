package taskTracker.controller;

import taskTracker.repository.JSONRepository;
import taskTracker.enums.CommandStrategy;
import taskTracker.commands.*;
import taskTracker.model.Task;
import taskTracker.service.JSONTaskService;
import taskTracker.service.TaskService;
import taskTracker.utils.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TaskCLI {

    private final JSONTaskService taskService;
    private final BufferedReader reader;
    private final Printer<Task> printer;

    public TaskCLI(JSONTaskService taskService, Printer<Task> printer) {
        this.taskService = taskService;
        this.printer = printer;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        try {
            taskService.readTasks();
        } catch (Exception e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        while (true) {
            try {
                System.out.print("task-cli ");
                String line = reader.readLine();
                if (line == null || line.trim().isEmpty()) {
                    continue;
                }
                handleCommand(line);
            } catch (IOException e) {
                System.out.println("Error reading input: " + e.getMessage());
            }
        }
    }

    // Method to handle command execution
    private void handleCommand(String line) {
        String command = line.split(" ")[0].toUpperCase();

        try {
            switch (command) {
                case "ADD" -> executeAddCommand(line);
                case "LIST" -> executeListCommand(line);
                case "DELETE" -> executeDeleteCommand(line);
                case "UPDATE" -> executeUpdateCommand(line);
                case "MARK-IN-PROGRESS" -> executeMarkInProgressCommand(line);
                case "MARK-DONE" -> executeMarkDoneCommand(line);
                default -> printAvailableCommands();
            }

            taskService.saveTasks();
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }

    private void executeAddCommand(String line) {
        AddCommand addCommand = new AddCommand(line);
        addCommand.execute(taskService);
    }

    private void executeListCommand(String line) {
        if (line.split(" ").length == 1) {
            ListAllCommand listAllCommand = new ListAllCommand(CommandStrategy.LIST, printer);
            listAllCommand.execute(taskService);
        } else {
            String listType = line.split(" ")[1].toUpperCase();
            switch (listType) {
                case "DONE" -> {
                    ListDoneCommand listDoneCommand = new ListDoneCommand(CommandStrategy.LIST_DONE, printer);
                    listDoneCommand.execute(taskService);
                }
                case "TODO" -> {
                    ListToDoCommand listToDoCommand = new ListToDoCommand(CommandStrategy.LIST_TODO, printer);
                    listToDoCommand.execute(taskService);
                }
                case "IN-PROGRESS" -> {
                    ListInProgressCommand listInProgressCommand = new ListInProgressCommand(CommandStrategy.LIST_IN_PROGRESS, printer);
                    listInProgressCommand.execute(taskService);
                }
                default -> System.out.println("Options are: done, todo, in-progress");
            }
        }
    }

    private void executeDeleteCommand(String line) {
        DeleteCommand deleteCommand = new DeleteCommand(line);
        deleteCommand.execute(taskService);
    }

    private void executeUpdateCommand(String line) {
        UpdateCommand updateCommand = new UpdateCommand(line);
        updateCommand.execute(taskService);
    }

    private void executeMarkInProgressCommand(String line) {
        MarkInProgressCommand markInProgressCommand = new MarkInProgressCommand(line);
        markInProgressCommand.execute(taskService);
    }

    private void executeMarkDoneCommand(String line) {
        MarkDoneCommand markDoneCommand = new MarkDoneCommand(line);
        markDoneCommand.execute(taskService);
    }

    private void printAvailableCommands() {
        System.out.println("Available commands are:");
        System.out.println("add");
        System.out.println("update");
        System.out.println("delete");
        System.out.println("mark-in-progress");
        System.out.println("mark-done");
        System.out.println("list");
    }
}
