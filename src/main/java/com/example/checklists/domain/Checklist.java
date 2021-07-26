package com.example.checklists.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Checklist {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Column
    @Getter
    @Setter
    private String name;

    @OneToMany(mappedBy = "checklist")
    @Getter
    @Setter
    @JsonIgnore
    private List<Task> taskList;

    @OneToMany(mappedBy = "checklist")
    @Getter
    @Setter
    @JsonIgnore
    private List<ChecklistInstance> checklistInstanceList;

    public Checklist() {
    }

    public Checklist(String name) {
        setName(name);
    }


}