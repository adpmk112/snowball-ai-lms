package com.ace.ai.admin.dtomodel;

import java.util.List;

import lombok.Data;

@Data
public class AdminBatchAttendanceDTO {
    private int batchId;
    private List<StudentAttendanceDTO> adminDashboardDTO;
}
