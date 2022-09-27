package com.ace.ai.student.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AssignmentFileDTO {
    private int assignmentId;
    private int studentId;
    private MultipartFile assignmentFile;
    private boolean notification;
    private int chapterId;

    private MultipartFile fileName;

}
