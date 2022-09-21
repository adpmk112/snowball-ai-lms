package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.BatchExamForm;

@Repository
public interface BatchExamFormRepository extends JpaRepository<BatchExamForm,Integer>{
    public List<BatchExamForm> findByDeleteStatusAndBatch_IdAndExamForm_DeleteStatus(Boolean delStatus,Integer batchId, Boolean status);
    public BatchExamForm findByBatch_IdAndExamForm_Id(int batchId, int examId);
}
