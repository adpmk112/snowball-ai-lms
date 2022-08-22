package com.ace.ai.admin.dtomodel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class ClassroomDTO {
    private LocalDate date;
    private String link;
    private LocalTime time;
    private LocalDateTime dateTime;
    private String teacherName;
    private String status;
    private String recordedVideo;
}
