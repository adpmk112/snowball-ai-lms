package com.ace.ai.admin.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ace.ai.admin.datamodel.Classroom;

@Repository
public interface ClassRoomRepository extends JpaRepository<Classroom,Integer> {
    
    public List<Classroom> findAllByBatchIdAndDeleteStatus(Integer batchId,boolean deleteStatus);
    public List<Classroom> findIdByBatchIdAndDeleteStatus(Integer batchId,boolean deleteStatus);
    public List<Classroom> findDateByBatchIdAndDeleteStatus(Integer batchId,boolean deleteStatus);
    public List<Classroom> findAllByDeleteStatusAndBatchIdOrderByIdAsc(boolean status, int batchId);
}
