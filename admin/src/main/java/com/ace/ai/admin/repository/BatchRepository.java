package com.ace.ai.admin.repository;


import com.ace.ai.admin.datamodel.Batch;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
    Batch findBatchById(Integer id);

    List<Batch> findByDeleteStatus(Boolean deleteStatus);

    Batch findBatchByName(String name);

    @Query(value = "SELECT * FROM batch WHERE id=(SELECT max(id) FROM batch);", nativeQuery = true)
    Batch findLastBatch();
    @Query(value = "SELECT count(*)from batch",nativeQuery = true)
    Integer getTotalBatches();

}

