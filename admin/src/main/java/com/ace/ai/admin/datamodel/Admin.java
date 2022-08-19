package com.ace.ai.admin.datamodel;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Admin implements Serializable {
    @Id
    private int id;
    private String name;
    private String photo;
    private String code;
    private String password;
    private boolean deleteStatus;
}
