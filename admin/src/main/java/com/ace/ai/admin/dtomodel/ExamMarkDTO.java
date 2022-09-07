package com.ace.ai.admin.dtomodel;

import java.util.List;

import com.ace.ai.admin.datamodel.ExamForm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamMarkDTO {
    private ExamForm exam;
    List<StudentIdMarkFilePathDTO> studentData;
    private int examId;
    private int batchId;

    public ExamMarkDTO(ExamForm exam, List<StudentIdMarkFilePathDTO> studentData, int examId ){
        this.exam = exam;
        this.studentData = studentData;
        this.examId = examId;
    }
}
