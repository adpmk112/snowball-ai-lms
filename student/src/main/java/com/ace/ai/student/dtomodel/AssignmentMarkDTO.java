package com.ace.ai.student.dtomodel;

import lombok.Data;

@Data
public class AssignmentMarkDTO {
    private int studentMark;
    private String submitDate;
    private String submitTime;
    private String fileName;
}
