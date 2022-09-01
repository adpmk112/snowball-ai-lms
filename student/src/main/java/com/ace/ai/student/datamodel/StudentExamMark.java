package com.ace.ai.student.datamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import lombok.Data;

@Entity
@Data
public class StudentExamMark implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String studentMark;
    private String uploadedFile;
    private Boolean notification;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "exam_form_id")
    private ExamForm examForm;
}
