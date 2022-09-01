package com.ace.ai.student.dtomodel;

import lombok.Data;

@Data
public class StudentAttendanceDTO {
    private int stuId;
    private String stuName;
    private int attendance;
    private int batchId;
   
}
