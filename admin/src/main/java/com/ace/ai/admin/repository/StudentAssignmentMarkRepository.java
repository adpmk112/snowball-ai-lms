package com.ace.ai.admin.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import  com.ace.ai.admin.datamodel.StudentAssignmentMark;
public interface StudentAssignmentMarkRepository extends JpaRepository<StudentAssignmentMark, Integer>{
    List<StudentAssignmentMark> findByAssignmentId(int assignmentId);
    StudentAssignmentMark findByAssignment_IdAndStudent_Id(int assignmentId, int studentId);
}
