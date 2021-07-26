package com.example.checklists.controller;

import com.example.checklists.domain.Checklist;
import com.example.checklists.repository.ChecklistInstanceRepository;
import com.example.checklists.repository.ChecklistRepository;
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
public class TestChecklistInstancesRestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private ChecklistInstanceRepository checklistInstanceRepository;

    @BeforeAll
    void setupAll() {
        Checklist checklist1 = new Checklist("List 1");
        checklistRepository.save(checklist1);
    }

    @AfterAll
    void cleanupAll() {
        checklistInstanceRepository.deleteAll();
        checklistRepository.deleteAll();
    }

    @Test
    @Disabled
    void testRespondsToExpectedRoutes() throws Exception {
        mockMvc.perform(post("/checklists/1/instances")
                .contentType("application/json"))
                .andExpect(status().isCreated());
    }
}
