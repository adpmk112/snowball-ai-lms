package com.ace.ai.admin.datamodel;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
public class Course implements Serializable  {
    @Id
    private int id;
    private String name;
    private String createdDate;
    private boolean deleteStatus;

    @OneToMany(mappedBy = "course")
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Batch> batches = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<ExamForm> examForms = new ArrayList<>();


}
