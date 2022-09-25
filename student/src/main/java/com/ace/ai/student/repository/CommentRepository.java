package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByBatchIdAndLocation(int batchId,String location);
    List<Comment> findByBatchIdAndLocationAndCommenterCode(int batchId,String location,String code);
}
