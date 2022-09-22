package com.ace.ai.student.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ChapterFileDTO {

    private int id;
    private String name;
    private  String fileType;
    private int chapterId;
    private int assignmentId;
   
}
