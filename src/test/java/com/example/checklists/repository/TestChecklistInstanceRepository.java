package com.example.checklists.repository;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.ChecklistInstance;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestChecklistInstanceRepository {

    private Checklist persistedChecklist1;
    private Checklist persistedChecklist2;
    private Checklist persistedChecklist3;

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private ChecklistInstanceRepository checklistInstanceRepository;

    @BeforeAll
    void setupAll() {
        Checklist checklist1 = new Checklist("List 1");
        Checklist checklist2 = new Checklist("List 2");
        Checklist checklist3 = new Checklist("List 3");
        persistedChecklist1 = checklistRepository.save(checklist1);
        persistedChecklist2 = checklistRepository.save(checklist2);
        persistedChecklist3 = checklistRepository.save(checklist3);

        ChecklistInstance instance1 = new ChecklistInstance();
        instance1.setChecklist(persistedChecklist1);
        checklistInstanceRepository.save(instance1);

        ChecklistInstance instance2 = new ChecklistInstance();
        instance2.setChecklist(persistedChecklist1);
        checklistInstanceRepository.save(instance2);

        ChecklistInstance instance3 = new ChecklistInstance();
        instance3.setChecklist(persistedChecklist2);
        checklistInstanceRepository.save(instance3);
    }

    @AfterAll
    void cleanup() {
        checklistInstanceRepository.deleteAll();
        checklistRepository.deleteAll();
    }

    @Test
    void testGetAllFindsAllCreated() {
        Iterable<ChecklistInstance> checklistInstances = checklistInstanceRepository.findAll();

        Collection<ChecklistInstance> checklistInstanceCollection = (Collection<ChecklistInstance>)checklistInstances;

        assertTrue(checklistInstanceCollection.size() == 3);

        assertNotNull(checklistInstanceCollection.stream().filter(instance -> persistedChecklist1.getId() == instance.getChecklist().getId()).findAny().orElse(null));
        assertNotNull(checklistInstanceCollection.stream().filter(instance -> persistedChecklist2.getId() == instance.getChecklist().getId()).findAny().orElse(null));
    }

    @Test
    void testGetAllDoesNotFindItemsNotAdded() {
        Iterable<ChecklistInstance> checklistInstances = checklistInstanceRepository.findAll();

        Collection<ChecklistInstance> checklistInstanceCollection = (Collection<ChecklistInstance>)checklistInstances;

        assertNull(checklistInstanceCollection.stream().filter(instance -> persistedChecklist3 == instance.getChecklist()).findAny().orElse(null));
    }

    @Test
    void testGetByIdFindsCorrectItem() {
        Optional<ChecklistInstance> foundInstance = checklistInstanceRepository.findById(1L);
        assertTrue(foundInstance.isPresent());
        assertEquals(foundInstance.get().getChecklist().getId(), persistedChecklist1.getId());

        foundInstance = checklistInstanceRepository.findById(2L);
        assertTrue(foundInstance.isPresent());
        assertEquals(foundInstance.get().getChecklist().getId(), persistedChecklist1.getId());

        foundInstance = checklistInstanceRepository.findById(3L);
        assertTrue(foundInstance.isPresent());
        assertEquals(foundInstance.get().getChecklist().getId(), persistedChecklist2.getId());
    }

    @Test
    void testGetByIdDoesNotFindItemNotAdded() {
        Optional<ChecklistInstance> foundInstance = checklistInstanceRepository.findById(4L);
        assertFalse(foundInstance.isPresent());
    }

    @Test
    void testFindByChecklistFindsAllAdded() {
        Iterable<ChecklistInstance> instances = checklistInstanceRepository.findByChecklist(persistedChecklist1);

        Collection<ChecklistInstance> instanceCollection = (Collection<ChecklistInstance>)instances;

        assertTrue(instanceCollection.size() == 2);

        instanceCollection.forEach(instance -> {
            assertEquals(instance.getChecklist().getId(), persistedChecklist1.getId());
        });
    }

    @Test
    void testFindByChecklistDoesNotFindItemsNotAdded() {
        Iterable<ChecklistInstance> instances = checklistInstanceRepository.findByChecklist(persistedChecklist1);

        Collection<ChecklistInstance> instanceCollection = (Collection<ChecklistInstance>)instances;

        assertTrue(instanceCollection.size() == 2);

        instanceCollection.forEach(instance -> {
            assertNotEquals(instance.getChecklist().getId(), persistedChecklist2.getId());
            assertNotEquals(instance.getChecklist().getId(), persistedChecklist3.getId());
        });
    }

    @Test
    void testSaveChecklistInstance() {
        ChecklistInstance newInstance = new ChecklistInstance();
        newInstance.setChecklist(persistedChecklist3);

        ChecklistInstance persistedInstance = checklistInstanceRepository.save(newInstance);
        assertEquals(persistedChecklist3.getId(), persistedInstance.getChecklist().getId());
        assertNotEquals(0, persistedInstance.getId());
    }
}
