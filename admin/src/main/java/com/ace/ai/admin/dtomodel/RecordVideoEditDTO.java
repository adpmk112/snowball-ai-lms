package com.ace.ai.admin.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RecordVideoEditDTO {
    private int id;
    private int classroomId;
    private MultipartFile recordVideo;
}
