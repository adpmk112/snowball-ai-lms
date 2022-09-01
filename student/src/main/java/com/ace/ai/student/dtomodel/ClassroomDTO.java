package com.ace.ai.student.dtomodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ClassroomDTO {
    private LocalDate date;
    private String link;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String teacherName;
    private String status;
    private Integer batchId;
    private String recordedVideo;
}
