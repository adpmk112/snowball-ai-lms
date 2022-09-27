package com.ace.ai.admin.repository;


import com.ace.ai.admin.datamodel.Batch;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface BatchRepository extends JpaRepository<Batch, Integer> {
    Batch findBatchById(Integer id);
    Batch findBatchByIdAndName(Integer id,String name);
    List<Batch> findByDeleteStatus(boolean deleteStatus);
    List<Batch> findByCourseId(int courseId);

    Batch findBatchByName(String name);

    @Query(value = "SELECT * FROM batch WHERE id=(SELECT max(id) FROM batch);", nativeQuery = true)
    Batch findLastBatch();
    @Query(value = "SELECT count(*)from batch",nativeQuery = true)
    Integer getTotalBatches();

    List<Batch> findByDeleteStatusAndCourseId(boolean deleteStatus, int courseId);

   

}

