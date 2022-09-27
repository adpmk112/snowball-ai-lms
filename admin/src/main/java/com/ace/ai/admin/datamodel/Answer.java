package com.ace.ai.admin.datamodel;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(length = 1000)
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
