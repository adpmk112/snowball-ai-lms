package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class VideoListCommentDTO {
    private int videoId;
    private String videoName;
    private int chapterId;
    private String chapterFileType;
}
