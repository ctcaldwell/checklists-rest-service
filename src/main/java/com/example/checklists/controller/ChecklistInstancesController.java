package com.example.checklists.controller;

import com.example.checklists.domain.ChecklistInstance;
import com.example.checklists.repository.ChecklistInstanceRepository;
import com.example.checklists.repository.ChecklistRepository;
import com.example.checklists.service.ChecklistInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.Date;

@RestController
public class ChecklistInstancesController {

    @Autowired
    private ChecklistInstanceService checklistInstanceService;

    @PostMapping("/checklists/{checklistId}/instances")
    public ResponseEntity<ChecklistInstance> create(
            UriComponentsBuilder uriBuilder,
            @PathVariable(value = "checklistId") Long checklistId) {

        ChecklistInstance persistedInstance = checklistInstanceService.createChecklistInstance(checklistId);

        UriComponents uriComponents = uriBuilder.path("/checklists/{checklistId}/instances/{id}").buildAndExpand(checklistId, persistedInstance.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(persistedInstance);
    }

    @GetMapping("/checklists/{checklistId}/instances")
    public Iterable<ChecklistInstance> getAllChecklistInstances(
            @PathVariable(value = "checklistId") Long checklistId) {
        return checklistInstanceService.findByChecklist(checklistId);
    }

    @GetMapping("/checklists/{checklistId}/instances/{id}")
    public ResponseEntity<ChecklistInstance> getChecklistInstanceById(@PathVariable("id") Long id) {
        return ResponseEntity.of(checklistInstanceService.getChecklistInstanceById(id));
    }
}