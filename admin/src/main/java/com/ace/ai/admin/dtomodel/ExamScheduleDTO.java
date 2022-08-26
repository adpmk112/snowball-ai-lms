package com.ace.ai.admin.dtomodel;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ExamScheduleDTO {
    private String examName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
