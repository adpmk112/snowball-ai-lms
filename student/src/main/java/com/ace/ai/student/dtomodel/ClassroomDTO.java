package com.ace.ai.student.dtomodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ClassroomDTO {
    private int id;
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
    private String attendanceStatus;
}
