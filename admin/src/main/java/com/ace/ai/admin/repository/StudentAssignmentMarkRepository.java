package com.ace.ai.admin.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import  com.ace.ai.admin.datamodel.StudentAssignmentMark;
public interface StudentAssignmentMarkRepository extends JpaRepository<StudentAssignmentMark, Integer>{
    
}
