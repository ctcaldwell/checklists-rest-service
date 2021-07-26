package com.example.checklists.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Task {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column
    @Getter
    @Setter
    private String description;

    @ManyToOne
    @JoinColumn(name="checklist_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Checklist checklist;

    @OneToMany(mappedBy = "task")
    @Getter
    @Setter
    @JsonIgnore
    private List<TaskInstance> taskInstanceList;

    public Task() {
    }

    public Task(String description) {
        setDescription(description);
    }
}