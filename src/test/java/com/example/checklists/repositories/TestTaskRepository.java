package com.example.checklists.repositories;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.Task;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestTaskRepository {

    private Checklist persistedChecklist1;
    private Checklist persistedChecklist2;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeAll
    void setupAll() {
        Checklist checklist1 = new Checklist("List 1");
        Checklist checklist2 = new Checklist("List 2");
        persistedChecklist1 = checklistRepository.save(checklist1);
        persistedChecklist2 = checklistRepository.save(checklist2);

        Task task1 = new Task();
        task1.setChecklist(persistedChecklist1);
        task1.setDescription("First Task");
        taskRepository.save(task1);

        Task task2 = new Task();
        task2.setChecklist(persistedChecklist2);
        task2.setDescription("Second Task");
        taskRepository.save(task2);
    }

    @AfterAll
    void cleanup() {
        taskRepository.deleteAll();
        checklistRepository.deleteAll();
    }

    @Test
    void testGetAllFindsAllCreated() {
        Iterable<Task> tasks = taskRepository.findAll();

        Collection<Task> taskCollection = (Collection<Task>)tasks;

        assertTrue(taskCollection.size() == 2);

        assertNotNull(taskCollection.stream().filter(task -> "First Task".equals(task.getDescription())).findAny().orElse(null));
        assertNotNull(taskCollection.stream().filter(task -> "Second Task".equals(task.getDescription())).findAny().orElse(null));
    }

    @Test
    void testGetAllDoesNotFindItemsNotAdded() {
        Iterable<Task> tasks = taskRepository.findAll();

        Collection<Task> taskCollection = (Collection<Task>)tasks;

        assertNull(taskCollection.stream().filter(task -> "Third Task".equals(task.getDescription())).findAny().orElse(null));
    }

    @Test
    void testGetByIdFindsCorrectItem() {
        Optional<Task> foundTask = taskRepository.findById(1L);
        assertTrue(foundTask.isPresent());

        foundTask = taskRepository.findById(2L);
        assertTrue(foundTask.isPresent());
    }

    @Test
    void testGetByIdDoesNotFindItemNotAdded() {
        Optional<Task> foundTask = taskRepository.findById(3L);
        assertFalse(foundTask.isPresent());
    }

    @Test
    void testFindByChecklistFindsAllAdded() {
        Iterable<Task> tasks = taskRepository.findByChecklist(persistedChecklist1);

        Collection<Task> taskCollection = (Collection<Task>)tasks;

        assertTrue(taskCollection.size() == 1);

        assertNotNull(taskCollection.stream().filter(task -> "First Task".equals(task.getDescription())).findAny().orElse(null));
    }

    @Test
    void testFindByChecklistDoesNotFindItemsNotAdded() {
        Iterable<Task> tasks = taskRepository.findByChecklist(persistedChecklist1);

        Collection<Task> taskCollection = (Collection<Task>)tasks;
        assertNull(taskCollection.stream().filter(task -> "Second Task".equals(task.getDescription())).findAny().orElse(null));
        assertNull(taskCollection.stream().filter(task -> "Third Task".equals(task.getDescription())).findAny().orElse(null));
    }

    @Test
    void testSaveTask() {
        String taskDescription = "Fourth Task";
        Task task = new Task();
        task.setChecklist(persistedChecklist1);
        task.setDescription(taskDescription);

        Task persistedTask = taskRepository.save(task);
        assertEquals(taskDescription, persistedTask.getDescription());
        assertNotEquals(0, persistedTask.getId());
    }
}
