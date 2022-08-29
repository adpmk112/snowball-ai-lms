package com.ace.ai.admin.dtomodel;

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
