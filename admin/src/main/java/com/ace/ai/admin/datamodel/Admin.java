package com.ace.ai.admin.datamodel;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.beans.Transient;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;


@Entity
@Data
@NoArgsConstructor
public class Admin implements Serializable {
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
    private String email;
    private String resetPasswordToken;
    @Transient
    public String getImagePath(){
        if(photo == null || code == null)return null;
        return "/assets/img/" + code + "/" +photo;
    }
    public Admin(String name, String photo,String password){
        this.name=name;
        this.photo=photo;
        this.password=password;

    }
}
