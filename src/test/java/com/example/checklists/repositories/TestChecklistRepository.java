package com.example.checklists.repositories;

import com.example.checklists.domain.Checklist;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestChecklistRepository {

    @Autowired
    private ChecklistRepository checklistRepository;

    @BeforeAll
    void setupAll() {
        Arrays.asList("List 1", "List 2", "List 3").forEach(name -> {
            Checklist checklist = new Checklist(name);
            checklistRepository.save(checklist);
        });
    }

    @AfterAll
    void cleanup() {
        checklistRepository.deleteAll();
    }

    @Test
    void testGetAllFindsAllCreated() {
        Iterable<Checklist> checklists = checklistRepository.findAll();

        Collection<Checklist> checklistCollection = (Collection<Checklist>)checklists;

        assertTrue(checklistCollection.size() == 3);

        assertNotNull(checklistCollection.stream().filter(checklist -> "List 1".equals(checklist.getName())).findAny().orElse(null));
        assertNotNull(checklistCollection.stream().filter(checklist -> "List 2".equals(checklist.getName())).findAny().orElse(null));
        assertNotNull(checklistCollection.stream().filter(checklist -> "List 3".equals(checklist.getName())).findAny().orElse(null));
    }

    @Test
    void testGetAllDoesNotFindItemsNotAdded() {
        Iterable<Checklist> checklists = checklistRepository.findAll();

        Collection<Checklist> checklistCollection = (Collection<Checklist>)checklists;

        assertNull(checklistCollection.stream().filter(checklist -> "List 4".equals(checklist.getName())).findAny().orElse(null));
    }

    @Test
    void testGetByIdFindsCorrectItem() {
        Optional<Checklist> foundChecklist = checklistRepository.findById(1L);
        assertTrue(foundChecklist.isPresent());

        foundChecklist = checklistRepository.findById(2L);
        assertTrue(foundChecklist.isPresent());

        checklistRepository.findById(3L);
        assertTrue(foundChecklist.isPresent());
    }

    @Test
    void testGetByIdDoesNotFindItemNotAdded() {
        Optional<Checklist> foundChecklist = checklistRepository.findById(4L);
        assertFalse(foundChecklist.isPresent());
    }

    @Test
    void testSaveChecklist() {
        String listName = "List 1";

        Checklist checklist = new Checklist(listName);
        Checklist persistedChecklist = checklistRepository.save(checklist);
        assertEquals(listName, persistedChecklist.getName());
        assertNotEquals(0, persistedChecklist.getId());
    }
}
