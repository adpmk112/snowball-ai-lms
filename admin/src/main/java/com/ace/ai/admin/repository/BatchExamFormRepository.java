package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.BatchExamForm;

@Repository
public interface BatchExamFormRepository extends JpaRepository<BatchExamForm,Integer>{
    public List<BatchExamForm> findByDeleteStatusAndBatch_IdAndExamForm_DeleteStatus(boolean delStatus,Integer batchId, boolean status);
    public List<BatchExamForm> findByDeleteStatusAndBatchId(boolean deleteStatus , Integer batchId);
    public BatchExamForm findByDeleteStatusAndBatchIdAndExamFormId(boolean deleteStatus, Integer batchId, int examFormId);
    public BatchExamForm findByExamForm_IdAndBatch_Id(int examFormId, int batchId);
}
