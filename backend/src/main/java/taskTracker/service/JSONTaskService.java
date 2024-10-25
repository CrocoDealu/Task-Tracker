package taskTracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskTracker.repository.JSONRepository;

@Service
public class JSONTaskService extends TaskService{
    public JSONTaskService(JSONRepository taskMapContainer) {
        super(taskMapContainer);
    }

    public void readTasks() {
        repository.read();
    }

    public void saveTasks() {
        repository.save();
    }
}
