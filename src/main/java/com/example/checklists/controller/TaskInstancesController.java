package com.example.checklists.controller;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.ChecklistInstance;
import com.example.checklists.domain.TaskInstance;
import com.example.checklists.repository.ChecklistInstanceRepository;
import com.example.checklists.repository.TaskInstanceRepository;
import com.example.checklists.service.ChecklistInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class TaskInstancesController {

    @Autowired
    private TaskInstanceRepository taskInstanceRepository;

    @Autowired
    private ChecklistInstanceRepository checklistInstanceRepository;

    @GetMapping("/checklists/{checklistId}/instances/{checklistInstanceId}/taskInstances")
    public Iterable<TaskInstance> getAllTaskInstances(
            @PathVariable(value = "checklistId") Long checklistId,
            @PathVariable(value = "checklistInstanceId") Long checklistInstanceId) {

        Optional<ChecklistInstance> checklistInstance = checklistInstanceRepository.findById(checklistInstanceId);
        if (checklistInstance.isEmpty()) {
            throw new ChecklistNotFoundException("ChecklistId " + checklistId + "not found");
        }

        return taskInstanceRepository.findByChecklistInstance(checklistInstance.get());
    }

//    @GetMapping("/checklists/{checklistId}/instances/{id}")
//    public ResponseEntity<ChecklistInstance> getChecklistInstanceById(@PathVariable("id") Long id) {
//        return ResponseEntity.of(checklistInstanceService.getChecklistInstanceById(id));
//    }
}
