package com.example.checklists.controller;

import com.example.checklists.domain.Checklist;
import com.example.checklists.repository.ChecklistRepository;
import com.example.checklists.repository.TaskRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestTasksRestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeAll
    void setupAll() {
        Checklist checklist1 = new Checklist("List 1");
        checklistRepository.save(checklist1);
    }

    @AfterAll
    void cleanupAll() {
        taskRepository.deleteAll();
        checklistRepository.deleteAll();
    }

    @Test
    void testRespondsToExpectedRoutes() throws Exception {
        mockMvc.perform(post("/checklists/1/tasks")
                .content("\"description\":\"Test Task 1\"")
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }
}
