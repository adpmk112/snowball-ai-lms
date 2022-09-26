package com.ace.ai.student.datamodel;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(length = 2000)
    private String answer;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @ManyToOne()
    @JoinColumn(name="question_id")
    private Question question;

    public Answer(String answer, boolean deleteStatus, Question question){
        this.answer = answer;
        this.deleteStatus = deleteStatus;
        this.question = question;
    
    }

}
