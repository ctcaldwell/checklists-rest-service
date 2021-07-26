package com.example.checklists.repository;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.ChecklistInstance;
import com.example.checklists.domain.Task;
import com.example.checklists.domain.TaskInstance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskInstanceRepository extends CrudRepository<TaskInstance, Long> {
    List<TaskInstance> findByChecklistInstance(ChecklistInstance checklistInstance);
}
