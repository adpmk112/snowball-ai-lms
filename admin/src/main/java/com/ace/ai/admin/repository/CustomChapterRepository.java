package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.CustomChapter;

@Repository
public interface CustomChapterRepository extends JpaRepository<CustomChapter,Integer> {
    CustomChapter findByName(String name);
    CustomChapter findByNameAndBatchId(String name,int batchId);
    List<CustomChapter> findByBatchIdAndDeleteStatus(int batchId,boolean deleteStatus);
}
