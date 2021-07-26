package com.example.checklists.service;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.ChecklistInstance;
import com.example.checklists.domain.Task;
import com.example.checklists.repository.ChecklistInstanceRepository;
import com.example.checklists.repository.ChecklistRepository;
import com.example.checklists.repository.TaskInstanceRepository;
import com.example.checklists.repository.TaskRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestChecklistInstanceService {

    private Checklist persistedChecklist;
    private Task persistedTask;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private ChecklistInstanceRepository checklistInstanceRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskInstanceRepository taskInstanceRepository;

    @Autowired
    private ChecklistInstanceService checklistInstanceService;

    @BeforeAll
    void setupAll() {
        Checklist checklist1 = new Checklist("List 1");
        persistedChecklist = checklistRepository.save(checklist1);

        Task task1 = new Task();
        task1.setChecklist(persistedChecklist);
        task1.setDescription("First Task");
        persistedTask = taskRepository.save(task1);
    }

    @AfterAll
    void cleanup() {
        taskInstanceRepository.deleteAll();
        checklistInstanceRepository.deleteAll();
        taskRepository.deleteAll();
        checklistRepository.deleteAll();
    }

    @Test
    void testCreateChecklistInstance() {
        ChecklistInstance instance = checklistInstanceService.createChecklistInstance(persistedChecklist.getId());
        assertNotNull(instance);

        assertFalse(checklistInstanceRepository.findByChecklist(persistedChecklist).isEmpty());
        assertFalse(taskInstanceRepository.findByChecklistInstance(instance).isEmpty());
    }
}
