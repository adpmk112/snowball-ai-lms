package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class TeacherCommentPostDTO {
    private int id;
    private String commenterCode;
    private String location;
    private boolean notification;
    private String text;
    private int batchId;
    private int teacherId;
    //chapterId or customchapterId or exam id or assignment id
    private int locationId;
    private int chapterFileId;
    private String stuCodeForAssignment;
    

}
