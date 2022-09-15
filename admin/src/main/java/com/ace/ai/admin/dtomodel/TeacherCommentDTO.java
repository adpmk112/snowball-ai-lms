package com.ace.ai.admin.dtomodel;

import javax.websocket.Decoder.Text;

import lombok.Data;

@Data
public class TeacherCommentDTO {
    private String commenter_Name;
    private String text;
    private String location;
    private int batchId;
}
