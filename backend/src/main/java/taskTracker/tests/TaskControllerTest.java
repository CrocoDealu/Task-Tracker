package taskTracker.tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import taskTracker.dispatcher.CommandDispatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddTask() throws Exception {
        mockMvc.perform(post("/api/tasks")
                        .param("title", "Test Task")
                        .param("description", "Test Description")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("Command executed successfully"));
    }

    @Test
    public void testListTasks() throws Exception {
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk());
    }


    @Test
    public void testListDoneTasks() throws Exception {
        mockMvc.perform(get("/api/tasks/done"))
                .andExpect(status().isOk());
    }
}

