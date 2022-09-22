package com.ace.ai.student.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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
