package taskTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import taskTracker.repository.JSONRepository;
import taskTracker.model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import taskTracker.service.JSONTaskService;
import taskTracker.tests.TestAll;
import taskTracker.utils.CLIPrinter;
import taskTracker.utils.Printer;
import taskTracker.controller.TaskCLI;

@SpringBootApplication
public class Main {
    public static void main(String[] args) throws IOException {
        TestAll.test();
        SpringApplication.run(Main.class, args);
//        uncomment if you want the cli version
//        JSONRepository repository = new JSONRepository("src/main/java/taskTracker/tasks.json");
//        Printer<Task> printer = new CLIPrinter<>();
//        JSONTaskService service = new JSONTaskService(repository);
//        TaskCLI ui = new TaskCLI(service, printer);
//        ui.start();
    }
}