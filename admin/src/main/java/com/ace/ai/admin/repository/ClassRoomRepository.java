package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Classroom;

@Repository
public interface ClassRoomRepository extends JpaRepository<Classroom,Integer> {
    
    public List<Classroom> findAllByBatchId(Integer batchId);
    public List<Classroom> findIdByBatchId(Integer batchId);
    public List<Classroom> findDateByBatchId(Integer batchId);
}
