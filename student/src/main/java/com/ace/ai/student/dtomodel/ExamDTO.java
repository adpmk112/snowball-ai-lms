package com.ace.ai.student.dtomodel;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExamDTO {
    private int id;
    private int studentId;
    private String name;
    private String type;
    private String duration;
    private List<QuestionDTO> questions;
    private int totalPoint;
    private String answerFile ;

}
