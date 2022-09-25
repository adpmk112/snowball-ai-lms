package com.ace.ai.student.dtomodel;

import lombok.Data;

@Data
public class StuCommentPostDTO {
    private int id;
    private String commenterCode;
    private String location;
    private boolean notification;
    private String text;
    private int batchId;
    private int stuId;
    //chapterId or customchapterId or exam id or assignment id
    private int locationId;
    private int chapterFileId;
    private int chapterId;
    

}
