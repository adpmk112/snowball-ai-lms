package com.ace.ai.admin.datamodel;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Batch implements Serializable {
    @Id
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

    @Override
    public String toString() {
        return "Batch{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", deleteStatus=" + deleteStatus +
                ", createdDate='" + createdDate + '\'' +
                ", course=" + course +
                ", chapterBatches=" + chapterBatches +
                ", batchExamForms=" + batchExamForms +
                ", teacherBatches=" + teacherBatches +
                ", customChapters=" + customChapters +
                ", comments=" + comments +
                ", students=" + students +
                ", classrooms=" + classrooms +
                '}';
    }
}
