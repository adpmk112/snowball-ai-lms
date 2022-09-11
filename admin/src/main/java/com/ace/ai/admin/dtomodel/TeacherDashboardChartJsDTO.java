package com.ace.ai.admin.dtomodel;

import java.util.List;

import lombok.Data;

@Data
public class TeacherDashboardChartJsDTO {
   private int batchId;
   private List<TeacherDashboardChartDTO> studentAttendance;
}
