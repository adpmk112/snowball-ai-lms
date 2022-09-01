package com.ace.ai.student.datamodel;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Chapter implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(columnDefinition = "tinyint(1) default 0")
    private int deleteStatus = 0;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "chapter")
    private List<ChapterBatch> chapterBatches = new ArrayList<>();

    @OneToMany(mappedBy = "chapter")
    private List<ChapterFile> chapterFiles = new ArrayList<>();
}
