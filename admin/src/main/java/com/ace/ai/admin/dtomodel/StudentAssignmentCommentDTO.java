package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class StudentAssignmentCommentDTO {
    private int stuId;
    private String stuCode;
    private boolean notification;
    private String photo;
    private String stuName;
}
