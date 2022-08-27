package com.ace.ai.admin.datamodel;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String photo;
    private String code;
    private String password;
    private boolean deleteStatus;

    @OneToMany(mappedBy = "teacher")
    private List<TeacherBatch> teacherBatches = new ArrayList<>();

    @Transient
    public String getImagePath(){
        if(photo == null || code == null)return null;
        return "/assets/img/" + code + "/" +photo;
    }
}
