package com.ace.ai.admin.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Teacher {

    @Id
    private int id;
    private String name;
    private String photo;
    private String code;
    private String password;
    private boolean deleteStatus;

    @OneToMany(mappedBy = "teacher")
    private List<TeacherBatch> teacherBatches = new ArrayList<>();
}