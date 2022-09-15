package com.ace.ai.admin.datamodel;

import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String photo;
    private String code;
    private String password;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;
    private String email;
    private String reset_password_token;
    @Transient
    public String getImagePath(){
        if(photo == null || code == null)return null;
        return "/assets/img/" + code + "/" +photo;
    }
}
