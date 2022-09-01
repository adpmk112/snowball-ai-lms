package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Integer>{
    List<Reply> findByCommentId(int commentId);
}
