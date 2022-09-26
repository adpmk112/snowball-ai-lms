package com.ace.ai.admin.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;


import lombok.NoArgsConstructor;




import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchExamForm {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id; 
    private String startDate;
    private String endDate;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name="batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name="exam_form_id")
    private ExamForm examForm;

    @OneToMany(mappedBy = "batchExamForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentExamMark> studentExamMarks = new ArrayList<>();

    public  BatchExamForm(String startDate, String endDate, boolean status, Batch batch, ExamForm examForm){
        this.startDate = startDate;
        this.endDate = endDate;
        this.deleteStatus = status;
        this.batch = batch;
        this.examForm = examForm;
    }
    public  BatchExamForm(Batch batch, ExamForm examForm){       
        this.batch = batch;
        this.examForm = examForm;
    }
    public BatchExamForm(int id, String startDate, String endDate){
        this.id= id;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
