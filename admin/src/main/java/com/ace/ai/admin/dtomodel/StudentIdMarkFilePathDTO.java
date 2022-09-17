package com.ace.ai.admin.dtomodel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentIdMarkFilePathDTO {
    private int studentId;
    private int mark;
    private String filePath;

    public StudentIdMarkFilePathDTO(int studentId, int mark){
        this.studentId = studentId; 
        this.mark = mark;
    }
}
