package com.ace.ai.admin.datamodel;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

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


}
