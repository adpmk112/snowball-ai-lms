package com.ace.ai.admin.dtomodel;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AttendanceDTO {
    private String date;
    private int classId;
    private HashMap<Integer, String> studentAndAttend;
}
