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
    

}
