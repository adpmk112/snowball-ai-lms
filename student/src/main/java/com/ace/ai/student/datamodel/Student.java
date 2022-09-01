package com.ace.ai.student.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import lombok.Data;

@Entity
@Data
public class Student implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String photo;
    private String code;
    private String password;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<StudentExamMark> studentExamMarks = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<StudentAssignmentMark> studentAssignmentMarks = new ArrayList<>();
}
