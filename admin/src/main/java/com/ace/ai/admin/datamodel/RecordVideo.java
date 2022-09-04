package com.ace.ai.admin.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


import lombok.Data;

@Entity
@Data
public class RecordVideo {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(columnDefinition = "tinyint(1) default 0")
    private int deleteStatus = 0;

    @ManyToOne
    @JoinColumn(name = "classroom_id")
    private Classroom classroom;


}
