package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class TeacherReplyViewDTO {
    private int id;
    private String text;
    private String dateTime;
    private String commenterName;
    private String commenterCode;
    private boolean notification;
    private boolean deleteStatus;
    private int commentId;
    private String commenterPhoto;
}
