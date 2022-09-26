package com.ace.ai.student.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamForm implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String type;
    private String duration;
    private int maxMark;

    @Column(columnDefinition = "tinyint(1) default 0")

    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "examForm", cascade = CascadeType.REMOVE)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "examForm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchExamForm> batchExamForms = new ArrayList<>();

    // @OneToMany(mappedBy = "examForm")
    // private List<StudentExamMark> studentExamMarks = new ArrayList<>();
    
    public ExamForm(int id, String name, String type, String duration, int maxMark, Boolean deleteStatus, Course course){
        this.id = id;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.maxMark = maxMark;
        this.deleteStatus = deleteStatus;
        this.course = course;
    }
    public ExamForm( String name, String type, String duration, int maxMark, Boolean deleteStatus, Course course){
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.maxMark = maxMark;
        this.deleteStatus = deleteStatus;
        this.course = course;
    }
}
