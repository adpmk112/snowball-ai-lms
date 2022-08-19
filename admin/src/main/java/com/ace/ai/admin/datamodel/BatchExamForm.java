package com.ace.ai.admin.datamodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class BatchExamForm {
    
    @Id
    private int id; 
    private String startDate;
    private String endDate;
    private Boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name="batch_id")
    private Batch batch;

    @ManyToOne
    @JoinColumn(name="exam_form_id")
    private ExamForm examForm;
}
