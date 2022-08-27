package com.ace.ai.admin.datamodel;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String trueAnswer;
    private Boolean deleteStatus;
    private int point;

    @ManyToOne
    @JoinColumn(name="exam_form_id")
    private ExamForm examForm;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer>answers = new ArrayList<>();

public Question(int id, 
        String name, 
        String trueAnswer, 
        Boolean deleteStatus, 
        ExamForm examForm,
        int point ){
            this.id = id;
            this.name = name;
            this.trueAnswer = trueAnswer;
            this.deleteStatus = deleteStatus;
            this.examForm = examForm;
            this.point = point;
}

}
