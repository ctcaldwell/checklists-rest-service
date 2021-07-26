package com.example.checklists.controller;

import com.example.checklists.domain.ChecklistInstance;
import com.example.checklists.repository.ChecklistInstanceRepository;
import com.example.checklists.repository.ChecklistRepository;
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
    private ChecklistRepository checklistRepository;

    @Autowired
    private ChecklistInstanceRepository checklistInstanceRepository;

    @PostMapping("/checklists/{checklistId}/instances")
    public ResponseEntity<ChecklistInstance> create(
            UriComponentsBuilder uriBuilder,
            @PathVariable(value = "checklistId") Long checklistId) {

        ChecklistInstance newInstance = new ChecklistInstance();
        ChecklistInstance persistedInstance = checklistRepository.findById(checklistId).map(checklist -> {
            newInstance.setChecklist(checklist);
            newInstance.setCreatedDate(new Date());
            return checklistInstanceRepository.save(newInstance);
        }).orElseThrow(() -> new ChecklistNotFoundException("ChecklistId " + checklistId + "not found"));

        UriComponents uriComponents = uriBuilder.path("/checklists/{checklistId}/instances/{id}").buildAndExpand(checklistId, persistedInstance.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(persistedInstance);
    }

    @GetMapping("/checklists/{checklistId}/instances")
    public Iterable<ChecklistInstance> getAllChecklistInstances(
            @PathVariable(value = "checklistId") Long checklistId) {
        return checklistInstanceRepository.findByChecklist(checklistRepository.findById(checklistId).map(checklist -> {
            return checklist;
        }).orElseThrow(() -> new ChecklistNotFoundException("ChecklistId " + checklistId + "not found")));
    }

    @GetMapping("/checklists/{checklistId}/instances/{id}")
    public ResponseEntity<ChecklistInstance> getChecklistInstanceById(@PathVariable("id") Long id) {
        Optional<ChecklistInstance> instanceOptional = checklistInstanceRepository.findById(id);
        return ResponseEntity.of(instanceOptional);
    }
}