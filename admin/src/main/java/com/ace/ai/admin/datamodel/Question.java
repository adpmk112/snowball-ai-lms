package com.ace.ai.admin.datamodel;

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
public class Question {
    
    @Id
    private int id;
    private String name;
    private String trueAnswer;
    private Boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name="exam_form_id")
    private ExamForm examForm;

    @OneToMany(mappedBy = "question")
    private List<Answer>answers = new ArrayList<>();
}
