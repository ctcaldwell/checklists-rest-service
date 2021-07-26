package com.example.checklists.service;

import com.example.checklists.controller.ChecklistNotFoundException;
import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.ChecklistInstance;
import com.example.checklists.domain.Task;
import com.example.checklists.domain.TaskInstance;
import com.example.checklists.repository.ChecklistInstanceRepository;
import com.example.checklists.repository.ChecklistRepository;
import com.example.checklists.repository.TaskInstanceRepository;
import com.example.checklists.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ChecklistInstanceService {

    @Autowired
    private ChecklistRepository checklistRepository;

    @Autowired
    private ChecklistInstanceRepository checklistInstanceRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskInstanceRepository taskInstanceRepository;

    @Transactional
    public ChecklistInstance createChecklistInstance(Long checklistId) {
        Optional<Checklist> checklist = checklistRepository.findById(checklistId);
        if (checklist.isEmpty()) {
            throw new ChecklistNotFoundException("ChecklistId " + checklistId + "not found");
        }

        ChecklistInstance newInstance = new ChecklistInstance();
        newInstance.setChecklist(checklist.get());
        newInstance.setCreatedDate(new Date());

        ChecklistInstance persistedChecklistInstance = checklistInstanceRepository.save(newInstance);

        List<Task> taskList = taskRepository.findByChecklist(checklist.get());
        taskList.forEach(task -> {
            TaskInstance taskInstance = new TaskInstance();
            taskInstance.setTask(task);
            taskInstance.setChecklistInstance(persistedChecklistInstance);
            taskInstance.setCreatedDate(new Date());
            taskInstance.setComplete(false);

            taskInstanceRepository.save(taskInstance);
        });

        return persistedChecklistInstance;
    }

    public Iterable<ChecklistInstance> findByChecklist(Long checklistId) {
        return checklistInstanceRepository.findByChecklist(checklistRepository.findById(checklistId).map(checklist -> {
            return checklist;
        }).orElseThrow(() -> new ChecklistNotFoundException("ChecklistId " + checklistId + "not found")));
    }

    public Optional<ChecklistInstance> getChecklistInstanceById(Long id) {
        return checklistInstanceRepository.findById(id);
    }
}
