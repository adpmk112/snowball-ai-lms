package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.CustomChapter;

@Repository
public interface CustomChapterRepository extends JpaRepository<CustomChapter,Integer> {
    List<CustomChapter> findByBatchId(int batchId);
}
