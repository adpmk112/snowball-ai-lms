package com.ace.ai.admin.datamodel;

import javax.persistence.*;

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
