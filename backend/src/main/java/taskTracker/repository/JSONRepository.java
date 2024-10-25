package taskTracker.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import taskTracker.model.Task;

import java.io.*;
import java.util.List;

@Repository
public class JSONRepository extends TaskMapContainer {

    private final String filePath;
    private final Gson gson;

    @Autowired
    public JSONRepository(@Value("${tasktracker.filepath}") String filePath) {
        this.filePath = filePath;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void save() {
        List<Task> taskList = this.getAll();
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            gson.toJson(taskList, fileWriter);
        } catch (IOException e) {
            System.err.println("Failed to save tasks to file: " + e.getMessage());
        }
    }

    public void read() {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist. No data loaded.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<Task> tasks = gson.fromJson(reader, new TypeToken<List<Task>>(){}.getType());
            this.clear();
            tasks.forEach(this::add);
        } catch (IOException e) {
            System.err.println("Failed to read tasks from file: " + e.getMessage());
        }
    }
}
