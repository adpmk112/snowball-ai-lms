package com.ace.ai.admin.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AssignmentFileDTO {
    private int assignmentId;
    private int studentId;
    private MultipartFile assignmentFile;
    private boolean notification;
    
}
