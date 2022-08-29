package com.ace.ai.admin.datamodel;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Teacher {
<<<<<<< HEAD

    @Id    
    @GeneratedValue(strategy=GenerationType.IDENTITY)
=======
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
    private int id;
    private String name;
    private String photo;
    private String code;
    private String password;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @OneToMany(mappedBy = "teacher")
    private List<TeacherBatch> teacherBatches = new ArrayList<>();

    @Transient
    public String getImagePath(){
        if(photo == null || code == null)return null;
        return "/assets/img/" + code + "/" +photo;
    }
}
