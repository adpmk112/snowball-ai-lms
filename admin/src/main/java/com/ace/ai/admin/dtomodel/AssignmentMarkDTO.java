package com.ace.ai.admin.dtomodel;


import java.util.List;

import com.ace.ai.admin.datamodel.Assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentMarkDTO {
    private Assignment assignment;
    private List<StudentIdMarkFilePathDTO> studentData;
    private int batchId;
    private int assignmentId;

}
