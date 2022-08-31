package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class ReqClassroomDTO {
    private String date;
    private String link;
    private String startTime;
    private String endTime;
    //private LocalDateTime startDateTime;
    //private LocalDateTime endDateTime;
    private String teacherName;
    //private String status;
    private Integer batchId;
    private String recordedVideo;
}
