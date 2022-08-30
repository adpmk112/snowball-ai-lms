package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class StudentAttendanceDTO {
    private int stuId;
    private String stuName;
    private int attendance;
    private int batchId;
   
}
