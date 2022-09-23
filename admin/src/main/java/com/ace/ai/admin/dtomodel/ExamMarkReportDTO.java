package com.ace.ai.admin.dtomodel;

import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.StudentExamMark;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
public class ExamMarkReportDTO {

    private String batchName;
    private String courseName;
    private List<ExamMarkDTO> examMarkDTOList;
    private List<Student> students;
}
