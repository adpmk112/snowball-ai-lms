package com.ace.ai.student.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FileUploadDTO {
    private int courseId;
    private String name;
    private MultipartFile[] video;
    private MultipartFile[] pdf;
    private MultipartFile[] assignment;
}
