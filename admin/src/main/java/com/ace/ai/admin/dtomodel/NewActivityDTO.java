package com.ace.ai.admin.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class NewActivityDTO {
    private int id;
    private int batchId;
    private String activityName;
    private MultipartFile[] video;
    private MultipartFile[] pdf;
    private MultipartFile[] assignment;
}
