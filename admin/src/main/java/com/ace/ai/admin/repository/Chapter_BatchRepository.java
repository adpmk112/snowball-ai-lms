package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.Chapter_Batch;
import com.ace.ai.admin.dtomodel.ChapterDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Chapter_BatchRepository extends JpaRepository<Chapter_Batch,Integer> {

    @Query(value = "select * from chapter_batch\n" +
            "where chapter_batch.batch_id= :id ", nativeQuery = true)
    public List<Chapter_Batch> findChapterIdByBatchId(Integer id);



}
