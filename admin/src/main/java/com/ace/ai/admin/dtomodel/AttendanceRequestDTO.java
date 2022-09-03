package com.ace.ai.admin.dtomodel;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequestDTO {
    private int batchId;
    private int classId;
    private List<StudentAttendDTO> studentAndAttendList = new ArrayList<>();
}
