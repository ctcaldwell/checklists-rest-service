package com.example.checklists.repository;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.ChecklistInstance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChecklistInstanceRepository extends CrudRepository<ChecklistInstance, Long> {
    List<ChecklistInstance> findByChecklist(Checklist checklist);
}
