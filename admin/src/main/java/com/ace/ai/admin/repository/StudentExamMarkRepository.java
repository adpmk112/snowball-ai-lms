package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.StudentExamMark;

public interface StudentExamMarkRepository extends JpaRepository<StudentExamMark,Integer>{
    public List<StudentExamMark> findByExamForm_Id(int examId);
    public List<StudentExamMark> findByExamForm_IdOrderByStudent_IdAsc(int examId);
    public StudentExamMark findByExamForm_IdAndStudent_Id(int examId, int studentId);
}
