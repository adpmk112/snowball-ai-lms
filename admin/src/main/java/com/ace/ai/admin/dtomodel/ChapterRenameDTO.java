package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class ChapterRenameDTO {
    private int id;
    private int courseId;
    private String name;
}
