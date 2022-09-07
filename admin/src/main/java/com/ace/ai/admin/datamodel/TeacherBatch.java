package com.ace.ai.admin.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TeacherBatch {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;
    public  TeacherBatch(){

    }
    public TeacherBatch(boolean deleteStatus, Teacher teacher, Batch batch) {
        this.deleteStatus = deleteStatus;
        this.teacher = teacher;
        this.batch = batch;
    }
}
