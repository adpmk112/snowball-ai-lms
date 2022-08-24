package com.ace.ai.admin.dtomodel;

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
    private List<String> answer_list;
    private String correct_answer;
    private String point;

}
