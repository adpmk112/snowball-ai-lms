package com.ace.ai.admin.dtomodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ClassroomDTO {
    private Integer id;
    private LocalDate date;
    private String link;
    private String startTime;
    private String endTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String teacherName;
    private String status;
    private Integer batchId;
    private String recordedVideo;
}
