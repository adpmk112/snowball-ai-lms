package com.ace.ai.admin.datamodel;

import java.beans.Transient;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Teacher {


    @Id    
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private int id;
    private String name;
    private String photo;
    private String code;
    @Lob
    @Column(length = 8129)
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
     public Teacher(){
        
     }
    public Teacher(String name, String photo, String code, String password, boolean deleteStatus) {
        this.name = name;
        this.photo = photo;
        this.code = code;
        this.password = password;
        this.deleteStatus = deleteStatus;
    }
    public Teacher(int id,String code,String name,String photo,String password,boolean deleteStatus){
        this.id = id;
        this.code = code;
        this.name = name;
        this.photo = photo;
        this.password = password;
        this.deleteStatus = deleteStatus;
    }


    
}
