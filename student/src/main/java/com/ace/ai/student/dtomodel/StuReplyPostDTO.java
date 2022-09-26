package com.ace.ai.student.dtomodel;

import lombok.Data;

@Data
public class StuReplyPostDTO {
    private int id;
    private String text;
    private String dateTime;
    private String commenterCode;
    private boolean notification;
    private int commentId;
    private boolean deleteStatus;
    private int stuId;
    private int batchId;
    //chapterId or customchapterId or exam id or assignment id
    private int locationId;
    private int chapterFileId;
    private int chapterId;
}
