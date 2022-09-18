package com.ace.ai.student.dtomodel;

import com.ace.ai.student.datamodel.ExamForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentExamDTO {
    private ExamForm exam;    
    private int mark;
    private String startDate;
    private String endDate;
    private String answerDate;
    private String status;
}
