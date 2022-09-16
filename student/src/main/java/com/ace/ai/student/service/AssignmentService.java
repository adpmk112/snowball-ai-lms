package com.ace.ai.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Assignment;
import com.ace.ai.student.datamodel.StudentAssignmentMark;
import com.ace.ai.student.dtomodel.AssignmentDateTimeDTO;
import com.ace.ai.student.dtomodel.StudentMarkDTO;
import com.ace.ai.student.repository.AssignmentRepository;
import com.ace.ai.student.repository.StudentAssignmentMarkRepository;


@Service
public class AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    StudentAssignmentMarkRepository studentAssignmentMarkRepository;
    
    public AssignmentDateTimeDTO getDateTimeByAssignmentId(int assignmentId){
        Assignment assignment = assignmentRepository.findById(assignmentId);
        AssignmentDateTimeDTO assignmentDateTimeDTO = new AssignmentDateTimeDTO();
        String end_date = assignment.getEnd_date();
        String end_time = assignment.getEnd_time();
        assignmentDateTimeDTO.setEnd_date(end_date);
        assignmentDateTimeDTO.setEnd_time(end_time);
        return assignmentDateTimeDTO;
    }

    public StudentMarkDTO getStudentMarkByAssiIdAndStuId(int assignmentId, int studentId){
        StudentAssignmentMark studentAssignmentMark =studentAssignmentMarkRepository.findByAssignmentIdAndStudentId(assignmentId , studentId);
        StudentMarkDTO studentMarkDTO = new StudentMarkDTO();
        studentMarkDTO.setStudentMark(String.valueOf(studentAssignmentMark.getStudentMark()));
        return studentMarkDTO;
    }

    }

