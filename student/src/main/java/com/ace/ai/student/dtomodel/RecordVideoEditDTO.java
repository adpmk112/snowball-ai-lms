package com.ace.ai.student.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RecordVideoEditDTO {
    private int id;
    private int classroomId;
    private MultipartFile recordVideo;
}
