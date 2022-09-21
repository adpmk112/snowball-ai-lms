package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Integer>{
    List<Reply> findByCommentId(int commentId);
}
