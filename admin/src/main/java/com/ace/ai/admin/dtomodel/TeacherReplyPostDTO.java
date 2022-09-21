package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class TeacherReplyPostDTO {
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
    private String stuCodeForAssignment;
    
}
