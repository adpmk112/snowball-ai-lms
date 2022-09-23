package com.ace.ai.student.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RecordVideoDTO {
    private int classroomId;
    private MultipartFile[] recordVideo;
}

