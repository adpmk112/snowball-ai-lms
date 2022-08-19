package com.ace.ai.admin.datamodel;

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Batch implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean deleteStatus;
    private String createdDate;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany(mappedBy = "batch")
    private List<ChapterBatch> chapterBatches = new ArrayList<>();

    @OneToMany(mappedBy = "batch")
    private List<BatchExamForm> batchExamForms = new ArrayList<>();

    @OneToMany(mappedBy = "batch")
    private List<TeacherBatch> teacherBatches = new ArrayList<>();

    @OneToMany(mappedBy = "batch")
    private List<CustomChapter> customChapters = new ArrayList<>();

    @OneToMany(mappedBy = "batch")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "batch")
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "batch")
    private List<Classroom> classrooms = new ArrayList<>();
    
}
