package com.ace.ai.student.dtomodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentExamDoneDTO {
    private String name;
    private String type;
    private int mark;
    private int maxMark;
}
