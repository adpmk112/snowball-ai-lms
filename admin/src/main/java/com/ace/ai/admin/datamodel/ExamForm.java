package com.ace.ai.admin.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class ExamForm implements Serializable {
    @Id
    private int id;
    private String name;
    private String type;
    private String maxMark;
    private Boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "examForm")
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "examForm")
    private List<BatchExamForm> batchExamForms = new ArrayList<>();

    @OneToMany(mappedBy = "examForm")
    private List<StudentExamMark> studentExamMarks = new ArrayList<>();
}
