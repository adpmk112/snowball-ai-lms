package com.ace.ai.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Comment;
import com.ace.ai.student.datamodel.Reply;
import com.ace.ai.student.dtomodel.StuCommentViewDTO;
import com.ace.ai.student.dtomodel.StuReplyViewDTO;
import com.ace.ai.student.repository.CommentRepository;
import com.ace.ai.student.repository.ReplyRepository;

@Service
public class StudentCommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;

    public List<StuCommentViewDTO> getCommentListByBatchIdAndLocation(int batchId,String location){
        List<StuCommentViewDTO> stuCommentViewDTOList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByBatchIdAndLocation(batchId, location);
        for(Comment comment : commentList){
            StuCommentViewDTO stuCommentViewDTO = new StuCommentViewDTO();
            List<Reply> replyList =replyRepository.findByCommentId(comment.getId());
            List<StuReplyViewDTO> stuReplyViewDTOList = new ArrayList<>();
            for(Reply reply : replyList){
                StuReplyViewDTO stuReplyViewDTO = new StuReplyViewDTO();
                stuReplyViewDTO.setId(reply.getId());
                stuReplyViewDTO.setText(reply.getText());
                stuReplyViewDTO.setDateTime(reply.getDateTime());
                stuReplyViewDTO.setCommentId(reply.getComment().getId());
                stuReplyViewDTO.setCommenterCode(reply.getCommenterCode());
                stuReplyViewDTO.setDeleteStatus(reply.isDeleteStatus());
                stuReplyViewDTO.setNotification(reply.isNotification());

                /////////////////////////////////////////////

            }
            // stuCommentViewDTO.setStuReplayViewDTOList();
        }
        return  stuCommentViewDTOList;
    }
}
