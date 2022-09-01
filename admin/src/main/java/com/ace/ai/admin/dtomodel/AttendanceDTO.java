package com.ace.ai.admin.dtomodel;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import lombok.Data;

@Data
public class AttendanceDTO {
    private String date;
    private int classId;
    private List<HashMap<Integer, String>> studentAndAttend;
}
