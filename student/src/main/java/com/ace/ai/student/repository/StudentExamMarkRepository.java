package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ai.student.*;
import com.ace.ai.student.datamodel.StudentExamMark;

public interface StudentExamMarkRepository extends JpaRepository<StudentExamMark,Integer>{   
    public StudentExamMark findByExamForm_IdAndStudent_Id(int examId, int studentId);
}
