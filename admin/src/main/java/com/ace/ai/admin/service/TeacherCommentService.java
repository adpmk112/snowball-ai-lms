package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Comment;
import com.ace.ai.admin.dtomodel.CommentLocationDTO;
import com.ace.ai.admin.repository.CommentRepository;
import com.ace.ai.admin.repository.ReplyRepository;

@Service
public class TeacherCommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;

    public List<CommentLocationDTO> getCommentLocation(int batchId){
        List<Comment> commentList = commentRepository.findByDeleteStatusAndBatchId(false, batchId);
        List<CommentLocationDTO> commentLocationDTOList = new ArrayList<>();
        for(Comment comment : commentList){
            CommentLocationDTO commentLocationDTO = new CommentLocationDTO();
            commentLocationDTO.setId(comment.getId());
            commentLocationDTO.setLocation(comment.getLocation());
            commentLocationDTO.setBatchId(comment.getBatch().getId());
            commentLocationDTO.setCommenterCode(comment.getCommenterCode());
            commentLocationDTO.setDeleteStatus(comment.isDeleteStatus());
            commentLocationDTO.setNotification(comment.isNotification());
            commentLocationDTO.setText(comment.getText());
            commentLocationDTOList.add(commentLocationDTO);
        }

        return commentLocationDTOList;
    }

   
}
