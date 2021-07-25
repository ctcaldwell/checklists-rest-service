package com.example.checklists.repositories;

import com.example.checklists.domain.Checklist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChecklistRepository extends CrudRepository<Checklist, Long> {
}
