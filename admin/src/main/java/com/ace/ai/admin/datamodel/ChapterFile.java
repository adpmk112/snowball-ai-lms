package com.ace.ai.admin.datamodel;

import java.beans.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class ChapterFile implements Serializable  {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String fileType;
    @Column(columnDefinition = "tinyint(1) default 0")
    private int deleteStatus = 0;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @OneToMany(mappedBy = "comment")
    private List<Reply>replies = new ArrayList<>();

    @Transient
    public String getFilePath(){
        if(name == null || chapter.getId() == 0)return null;
        return "/assets/chapterFiles/" + chapter.getId() + "/" +name;
    }
}
