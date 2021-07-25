package com.example.checklists.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @JoinColumn(name="checklist_id")
    @Getter
    @Setter
    private Checklist checklist;

    public Task() {
    }

    public Task(String description) {
        setDescription(description);
    }
}