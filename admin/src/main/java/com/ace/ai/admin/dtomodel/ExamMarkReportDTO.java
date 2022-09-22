package com.ace.ai.admin.dtomodel;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
@Data
public class ExamMarkReportDTO {
    private List<String> examName;
    private String batchName;
    private String courseName;
    private List<Integer> studentMark;
    private List<String> studentName;
}
