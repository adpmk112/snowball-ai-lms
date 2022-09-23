package com.ace.ai.admin.dtomodel;

import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.StudentAssignmentMark;
import lombok.Data;

import java.util.List;
@Data
public class AssignmentReportDTO {
    private String batchName;
    private String courseName;
    private List<Student> studentList;
    private List<AssignmentMarkDTO> studentAssignmentMarks;
}
