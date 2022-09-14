package com.ace.ai.student.dtomodel;

import java.lang.reflect.Array;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuestionDTO {
    private int id;
    private String text;
    private List<String> answerList;
    private String correctAnswer;
    private int point;
    private String studentAnswer="#!$@!NoThing";
}
