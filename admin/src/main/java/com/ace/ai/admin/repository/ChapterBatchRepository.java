package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.ChapterBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterBatchRepository extends JpaRepository<ChapterBatch,Integer> {

    @Query(value = "select * from chapter_batch\n" +
            "where chapter_batch.batch_id= :id ", nativeQuery = true)
    public List<ChapterBatch> findChapterIdByBatchId(Integer id);
    List<ChapterBatch> findByBatchIdAndDeleteStatus(int batchId,int deleteStatus);
    ChapterBatch findChapterBatchByBatchIdAndChapterId(Integer batchId,Integer chapterId);
    ChapterBatch findByChapterIdAndBatchIdAndDeleteStatus(int chapterId,int batchId,int deleteStatus);
   


}
