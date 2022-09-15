package com.ace.ai.admin.dtomodel;

import java.util.List;

import lombok.Data;

@Data
public class TeacherDashboardExamDTO {
    private int examForm_id;
    private String examForm_name;
    private int max_marks;
    List<StudentExamMarkDTO> studentExamMarkDTO;
}
