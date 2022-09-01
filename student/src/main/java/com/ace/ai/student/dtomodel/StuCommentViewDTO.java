package com.ace.ai.student.dtomodel;

import java.util.List;

import lombok.Data;

@Data
public class StuCommentViewDTO {
    private int id;
    private String text;
    private String location;
    private String dateTime;
    private String commenterCode;
    private boolean notification;
    private List<StuReplyViewDTO> StuReplayViewDTOList;
}
