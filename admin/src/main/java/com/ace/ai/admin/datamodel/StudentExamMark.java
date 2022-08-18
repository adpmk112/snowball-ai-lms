package com.ace.ai.admin.datamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class StudentExamMark implements Serializable{
    
    @Id
    private int id;
    private String studentMark;
    private String uploadedFile;
    private Boolean notification;
    private Boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "exam_form_id")
    private ExamForm examForm;
}
