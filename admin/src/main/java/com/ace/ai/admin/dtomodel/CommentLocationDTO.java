package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class CommentLocationDTO {
    
    private int id;
    
    private String text;
    private String location;
    private String dateTime;
    private String commenterCode;
    private boolean notification;
    private boolean deleteStatus;
    private int batchId;
}
