package com.ace.ai.admin.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import javax.persistence.Column;

@Entity
@Data
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
}
