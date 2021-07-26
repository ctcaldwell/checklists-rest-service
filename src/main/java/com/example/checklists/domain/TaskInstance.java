package com.example.checklists.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class TaskInstance {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column
    @Getter
    @Setter
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name="task_id")
    @Getter
    @Setter
    private Task task;

    @ManyToOne
    @JoinColumn(name="checklist_instance_id")
    @Getter
    @Setter
    private ChecklistInstance checklistInstance;

    @Column
    @Getter
    @Setter
    private boolean complete;

    @Column
    @Getter
    @Setter
    private Date completedDate;

    public TaskInstance() {
    }
}