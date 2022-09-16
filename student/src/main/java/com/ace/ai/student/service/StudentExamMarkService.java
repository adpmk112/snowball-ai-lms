package com.ace.ai.student.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.StudentExamMark;
import com.ace.ai.student.repository.StudentExamMarkRepository;

@Service
public class StudentExamMarkService {
    @Autowired StudentExamMarkRepository studentExamMarkRepository;
    
    public StudentExamMark findByBatchExamForm_IdAndStudent_Id(int examId, int studentId){
        return studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(examId, studentId);
    }

    public void save(StudentExamMark studentExamMark){
        studentExamMarkRepository.save(studentExamMark);
    }
}
