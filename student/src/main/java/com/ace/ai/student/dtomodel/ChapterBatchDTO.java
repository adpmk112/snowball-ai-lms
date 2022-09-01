package com.ace.ai.student.dtomodel;

import lombok.Data;

@Data
public class ChapterBatchDTO {
    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private int deleteStatus;
    private int chapterId;
}
