package com.ace.ai.admin.dtomodel;

import java.util.List;

import lombok.Data;

@Data
public class TeaceherDashboardAssignmentDTO {
    private int batchId;
    private int assignmentId;
    private String assignmentName;
    private List<TeacherDashboardAssignmentStudentMarksDTO> teacherDashboardAssignmentStudentMarksDTO;
    
}
