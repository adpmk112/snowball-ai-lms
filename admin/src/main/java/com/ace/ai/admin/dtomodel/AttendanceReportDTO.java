package com.ace.ai.admin.dtomodel;

import com.ace.ai.admin.datamodel.Attendance;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
public class AttendanceReportDTO {
    private String courseName;
    private String batchName;
    private HashMap<Integer, String> studentAndAttend;
    private HashMap<Integer, String> studentNames;
    private List<String> dateList;
    private String teacherName;

}
