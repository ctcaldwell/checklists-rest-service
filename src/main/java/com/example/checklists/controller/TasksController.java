package com.example.checklists.controller;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.Task;
import com.example.checklists.repositories.ChecklistRepository;
import com.example.checklists.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.context.request.WebRequest;
import java.util.Optional;

@RestController
public class TasksController {

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("/checklists/{checklistId}/tasks")
    public ResponseEntity<Task> create(
            UriComponentsBuilder uriBuilder,
            @PathVariable(value = "checklistId") Long checklistId,
            @RequestBody Task task) {

        Task persistedTask = checklistRepository.findById(checklistId).map(checklist -> {
            task.setChecklist(checklist);
            return taskRepository.save(task);
        }).orElseThrow(() -> new ChecklistNotFoundException("ChecklistId " + checklistId + "not found"));

        UriComponents uriComponents = uriBuilder.path("/checklists/{checklistId}/tasks/{id}").buildAndExpand(checklistId, persistedTask.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(persistedTask);
    }

    @GetMapping("/checklists/{checklistId}/tasks")
    public Iterable<Task> getAllTasks(
            @PathVariable(value = "checklistId") Long checklistId) {

        return taskRepository.findByChecklist(checklistRepository.findById(checklistId).map(checklist -> {
            return checklist;
        }).orElseThrow(() -> new ChecklistNotFoundException("ChecklistId " + checklistId + "not found")));
    }

    @GetMapping("tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        return ResponseEntity.of(taskOptional);
    }
}