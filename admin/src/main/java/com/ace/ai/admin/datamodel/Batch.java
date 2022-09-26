package com.ace.ai.admin.datamodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean deleteStatus;
    private String createdDate;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChapterBatch> chapterBatches = new ArrayList<>();

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchExamForm> batchExamForms = new ArrayList<>();

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeacherBatch> teacherBatches = new ArrayList<>();
  
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomChapter> customChapters = new ArrayList<>();

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> students = new ArrayList<>();

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Classroom> classrooms = new ArrayList<>();

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignment> assignment = new ArrayList<>();

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
    public Batch(int id, String name, boolean deleteStatus, String createdDate, Course course){
        this.id = id;
        this.name = name;
        this.deleteStatus = deleteStatus;
        this.createdDate =  createdDate;
        this.course = course;
    }
   
}
