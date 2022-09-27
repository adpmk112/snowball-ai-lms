package com.ace.ai.admin.dtomodel;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExamDTO {
    private String id;
    private String course_id;
    private String name;
    private String type;
    private String duration;
    private List<QuestionDTO> questions;
    private String total_point;

    public ExamDTO( String id,
                    String name,
                    String type,
                    String duration,
                    List<QuestionDTO> questions,
                    String total_point){
            this.id = id;
            this.name = name;
            this.type=type;
            this.duration = duration;
            this.questions = questions;
            this.total_point = total_point;
                }

}
