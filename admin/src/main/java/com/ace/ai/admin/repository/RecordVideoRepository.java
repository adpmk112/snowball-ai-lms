package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.RecordVideo;

@Repository
public interface RecordVideoRepository extends JpaRepository<RecordVideo,Integer> {
    List<RecordVideo> findByDeleteStatusAndClassroomId(int deleteStatus,int classroomId);
    RecordVideo findById(int id);
    
}
