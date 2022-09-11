package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class TeacherDashboardExamDTO {
    private int examId;
    private String studentName;
    private int marks;
    private int exam_formId;
    private String examName;
}
