package com.example.checklists.controller;

import com.example.checklists.domain.Checklist;
import com.example.checklists.repository.ChecklistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriComponents;
import java.util.Optional;

@RestController
public class ChecklistsController {

    @Autowired
    private ChecklistRepository checklistRepository;

    @PostMapping("/checklists")
    public ResponseEntity<Checklist> create(
            UriComponentsBuilder uriBuilder,
            @RequestBody Checklist checklist) {

        Checklist persistedChecklist = checklistRepository.save(checklist);

        UriComponents uriComponents = uriBuilder.path("/checklists/{id}").buildAndExpand(persistedChecklist.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(persistedChecklist);
    }

    @GetMapping("/checklists")
    public Iterable<Checklist> getAllChecklists() {
        return checklistRepository.findAll();
    }

    @GetMapping("/checklists/{id}")
    public ResponseEntity<Checklist> getChecklistById(@PathVariable("id") Long id) {
        Optional<Checklist> checklistOptional = checklistRepository.findById(id);
        return ResponseEntity.of(checklistOptional);
    }
}