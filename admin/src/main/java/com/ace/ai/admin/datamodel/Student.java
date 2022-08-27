package com.ace.ai.admin.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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
