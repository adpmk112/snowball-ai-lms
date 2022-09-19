package com.ace.ai.admin.dtomodel;

import java.util.List;

import lombok.Data;

@Data
public class TeacherAssignmentViewDTO {
    private int batchId;
    private int assignmentId;
    private String stuCode;
    private List<String> teacherCode;
}
