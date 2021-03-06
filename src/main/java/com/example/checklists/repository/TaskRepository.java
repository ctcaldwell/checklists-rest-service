package com.example.checklists.repository;

import com.example.checklists.domain.Checklist;
import com.example.checklists.domain.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findByChecklist(Checklist checklist);
}
