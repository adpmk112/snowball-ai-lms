package com.ace.ai.admin.datamodel;

import javax.persistence.*;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;

@Entity
@Data
@ToString
public class Course implements Serializable  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String createdDate;
   
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @OneToMany(mappedBy = "course")
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Batch> batches = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<ExamForm> examForms = new ArrayList<>();
    public Course(){
        
    }
    public Course(String name, String createdDate, boolean deleteStatus) {
        this.name = name;
        this.createdDate = createdDate;
        this.deleteStatus = deleteStatus;
    }

    public Course(int id,String name,String createdDate, boolean deleteStatus){
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
        this.deleteStatus = deleteStatus;
    }
   


}
