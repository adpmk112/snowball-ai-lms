package com.ace.ai.admin.datamodel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Answer {
    
    @Id
    private int id;
    private String answer;
    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

}
