package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.ChapterBatch;

@Repository
public interface ChapterBatchRepository extends JpaRepository<ChapterBatch,Integer> {
    List<ChapterBatch> findByBatchId(int batchId);
}
