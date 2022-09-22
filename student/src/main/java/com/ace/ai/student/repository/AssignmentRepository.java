package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ace.ai.student.datamodel.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment , Integer>{
    Assignment findByIdAndDeleteStatus(int id, boolean deleteStatus);
    List<Assignment> findByAssignmentChapterNameAndDeleteStatusAndBatchId(String chapterName,boolean deleteStatus,int batchId);
}
