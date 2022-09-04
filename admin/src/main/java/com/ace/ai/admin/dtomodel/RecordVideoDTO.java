package com.ace.ai.admin.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class RecordVideoDTO {
    private int classroomId;
    private MultipartFile[] recordVideo;
}
