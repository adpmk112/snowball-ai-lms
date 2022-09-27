package com.ace.ai.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ace.ai.student.datamodel.StudentAssignmentMark;

@Repository
public interface StudentAssignmentMarkRepository extends JpaRepository<StudentAssignmentMark , Integer>{
    public StudentAssignmentMark findByAssignment_IdAndStudent_Id(int assignmentId,int studentId);
    public StudentAssignmentMark findByUploadedFile(String upLoadedFile);
    
}
