package com.ace.ai.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ace.ai.student.datamodel.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment , Integer>{
    Assignment findByIdAndDeleteStatus(int id, boolean deleteStatus);
}
