package com.ace.ai.student.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Question {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(length = 2000)
    private String name;
    @Column(length = 1000)
    private String trueAnswer;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;
    private int point;

    @ManyToOne
    @JoinColumn(name="exam_form_id")
    private ExamForm examForm;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Answer>answers = new ArrayList<>();

public Question(
        String name, 
        String trueAnswer, 
        Boolean deleteStatus, 
        ExamForm examForm,
        int point ){
            this.name = name;
            this.trueAnswer = trueAnswer;
            this.deleteStatus = deleteStatus;
            this.examForm = examForm;
            this.point = point;
}

}
