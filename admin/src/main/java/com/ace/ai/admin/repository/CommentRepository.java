package com.ace.ai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.ace.ai.admin.datamodel.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {
   List<Comment> findByNotificationAndDeleteStatusAndBatchId(boolean notification,boolean deleteStatus,int batchId);
   List<Comment> findByDeleteStatusAndBatchId(boolean deleteStatus,int batchId);
   List<Comment> findByBatchIdAndLocation(int batchId,String location);
   List<Comment> findByBatchIdAndLocationAndCommenterCode(int batchId,String location,String code);
}
