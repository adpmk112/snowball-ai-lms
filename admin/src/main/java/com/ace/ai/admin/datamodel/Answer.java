package com.ace.ai.admin.datamodel;

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
    private String answer;
    private boolean deleteStatus;

    @ManyToOne()
    @JoinColumn(name="question_id")
    private Question question;

}
