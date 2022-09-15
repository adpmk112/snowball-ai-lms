package com.ace.ai.admin.dtomodel;
import lombok.Data;

@Data
public class TeacherCommentDTO {
    private String commenter_Name;
    private String text;
    private String location;
    private int batchId;
    private int commentId;
}
