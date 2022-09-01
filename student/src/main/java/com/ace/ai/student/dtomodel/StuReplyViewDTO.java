package com.ace.ai.student.dtomodel;

import lombok.Data;

@Data
public class StuReplyViewDTO {
    private int id;
    private String text;
    private String dateTime;
    private String commenterCode;
    private boolean notification;
    private boolean deleteStatus;
    private int commentId;
}
