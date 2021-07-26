package com.example.checklists.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Auditable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table
public class ChecklistInstance {

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
    @JoinColumn(name="checklist_id", referencedColumnName = "id")
    @Getter
    @Setter
    private Checklist checklist;

    public ChecklistInstance() {
    }
}
