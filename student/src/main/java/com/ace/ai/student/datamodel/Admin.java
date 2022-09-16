package com.ace.ai.student.datamodel;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
    private String resetPasswordToken;

}
