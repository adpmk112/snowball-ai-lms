package com.ace.ai.admin.dtomodel;

import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Student;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
public class AttendanceReportDTO {
    private String courseName;
    private String batchName;
    private List<AttendanceDTO> attendanceDTOS;
    private List<Student> students;
    private  List<StudentAttendanceDTO> studentDTOList;

}
