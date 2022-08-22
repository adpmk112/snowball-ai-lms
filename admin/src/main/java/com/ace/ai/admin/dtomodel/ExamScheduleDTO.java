package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class ExamScheduleDTO {
    private String examName;
    private String startDate;
    private String endDate;
    private String status;
}
