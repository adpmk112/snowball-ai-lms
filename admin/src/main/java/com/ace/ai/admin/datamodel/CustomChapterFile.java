package com.ace.ai.admin.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class CustomChapterFile {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String fileType;
    private String filePath;
    private Boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "custom_chapter_id")
    private CustomChapter customChapter;
}
