package com.ace.ai.student.dtomodel;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
    private int id;//this is batchexamId
    private int studentId;
    private String name;
    private String type;
    private String duration;
    private List<QuestionDTO> questions;
    private int totalPoint;
    private MultipartFile answerFile ;

    public ExamDTO(int id, int studentId, String name, String type,
     String duration,
     List<QuestionDTO> questions,
     int totalPoint
     ){
        this.id= id;
        this.studentId = studentId;
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.questions = questions;
        this.totalPoint = totalPoint;
    }

}
