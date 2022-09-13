package com.ace.ai.admin.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CustomChapterFileDTO {
    private int id;
    private String name;
    private  String fileType;
    private MultipartFile file;
    private int deleteStatus;
    private int customChapterId;
}
