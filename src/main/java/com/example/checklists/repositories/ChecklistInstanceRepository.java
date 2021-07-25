package com.example.checklists.repositories;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.ChecklistInstance;
import com.example.checklists.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistInstanceRepository extends CrudRepository<ChecklistInstance, Long> {
    List<ChecklistInstance> findByChecklist(Checklist checklist);
}
