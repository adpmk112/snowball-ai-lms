package com.ace.ai.admin.dtomodel;

import java.util.List;

import lombok.Data;

@Data
public class TeacherCommentViewDTO {
    private int id;
    private String text;
    private String location;
    private String dateTime;
    private String commenterName;
    private String commenterCode;
    private boolean notification;
    private String commenterPhoto;
    private List<TeacherReplyViewDTO> teacherReplayViewDTOList;
}
