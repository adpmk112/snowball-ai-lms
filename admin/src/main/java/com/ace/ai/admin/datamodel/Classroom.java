package com.ace.ai.admin.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.Column;

import lombok.Data;

@Entity
@Data
public class Classroom implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String date;
    private String link;
    private String startTime;
    private String endTime;
    private String teacherName;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "classroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecordVideo> recordVideo = new ArrayList<>();
    
}
