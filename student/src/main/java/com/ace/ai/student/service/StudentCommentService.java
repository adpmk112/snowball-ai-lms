package com.ace.ai.student.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.Comment;
import com.ace.ai.student.datamodel.Reply;
import com.ace.ai.student.dtomodel.StuCommentPostDTO;
import com.ace.ai.student.dtomodel.StuCommentViewDTO;
import com.ace.ai.student.dtomodel.StuReplyPostDTO;
import com.ace.ai.student.dtomodel.StuReplyViewDTO;
import com.ace.ai.student.repository.BatchRepository;
import com.ace.ai.student.repository.CommentRepository;
import com.ace.ai.student.repository.ReplyRepository;
import com.ace.ai.student.repository.StudentRepository;
import com.ace.ai.student.repository.TeacherRepository;

@Service
public class StudentCommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;

    public List<StuCommentViewDTO> getCommentListByBatchIdAndLocation(int batchId, String location) {
        List<StuCommentViewDTO> stuCommentViewDTOList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByBatchIdAndLocation(batchId, location);
        for (Comment comment : commentList) {
            StuCommentViewDTO stuCommentViewDTO = new StuCommentViewDTO();
            List<Reply> replyList = replyRepository.findByCommentId(comment.getId());
            List<StuReplyViewDTO> stuReplyViewDTOList = new ArrayList<>();
            for (Reply reply : replyList) {
                StuReplyViewDTO stuReplyViewDTO = new StuReplyViewDTO();
                stuReplyViewDTO.setId(reply.getId());
                stuReplyViewDTO.setText(reply.getText());
                stuReplyViewDTO.setDateTime(reply.getDateTime());
                stuReplyViewDTO.setCommentId(reply.getComment().getId());
                String stuName = "";
                String teacherName ="";
                String stuPhoto = "";
                String  teacherPhoto = "";
                

                if(studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(),false)!=null){
                    stuName = studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(),false).getName();
                    stuPhoto = studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(),false).getPhoto();
                }
                else if(teacherRepository.findByCode(reply.getCommenterCode())!=null){
                    teacherName = teacherRepository.findByCode(reply.getCommenterCode()).getName();
                  teacherPhoto = teacherRepository.findByCode(reply.getCommenterCode()).getPhoto();
                }
                

                if (stuName.isBlank()) {
                    stuReplyViewDTO.setCommenterName(teacherName);
                    stuReplyViewDTO.setCommenterPhoto(teacherPhoto);
                } else if (teacherName.isBlank()) {
                    stuReplyViewDTO.setCommenterName(stuName);
                    stuReplyViewDTO.setCommenterPhoto(stuPhoto);
                }

                stuReplyViewDTO.setCommenterCode(reply.getCommenterCode());
                stuReplyViewDTO.setDeleteStatus(reply.isDeleteStatus());
                stuReplyViewDTO.setNotification(reply.isNotification());
                stuReplyViewDTOList.add(stuReplyViewDTO);

            }
            stuCommentViewDTO.setId(comment.getId());
            stuCommentViewDTO.setText(comment.getText());
            stuCommentViewDTO.setLocation(comment.getLocation());
            stuCommentViewDTO.setDateTime(comment.getDateTime());
            String stuName = "";
            String teacherName = "";
            String stuPhoto = "";
            String  teacherPhoto = "";
            if(studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(),false)!=null){
                stuName = studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(),false).getName();
                stuPhoto = studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(),false).getPhoto();
            }
            else if(teacherRepository.findByCode(comment.getCommenterCode())!=null){
                teacherName = teacherRepository.findByCode(comment.getCommenterCode()).getName();
                teacherPhoto = teacherRepository.findByCode(comment.getCommenterCode()).getPhoto();
            }
            

            if (stuName.isBlank()) {
                stuCommentViewDTO.setCommenterName(teacherName);
                stuCommentViewDTO.setCommenterPhoto(teacherPhoto);
            } else if (teacherName.isBlank()) {
                stuCommentViewDTO.setCommenterName(stuName);
                stuCommentViewDTO.setCommenterPhoto(stuPhoto);
                
            }
            stuCommentViewDTO.setCommenterCode(comment.getCommenterCode());
            stuCommentViewDTO.setNotification(comment.isNotification());
            stuCommentViewDTO.setStuReplayViewDTOList(stuReplyViewDTOList);
            stuCommentViewDTOList.add(stuCommentViewDTO);
        }
        return stuCommentViewDTOList;
    }

    public void saveComment(StuCommentPostDTO stuCommentPostDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Batch batch = batchRepository.findById(stuCommentPostDTO.getBatchId()).get();

        Comment comment = new Comment();
        comment.setCommenterCode(stuCommentPostDTO.getCommenterCode());
        comment.setDateTime(LocalDateTime.now().format(dtf));
        // location need to set in controller
        comment.setLocation(stuCommentPostDTO.getLocation());
        comment.setNotification(true);
        comment.setText(stuCommentPostDTO.getText());
        comment.setBatch(batch);
        commentRepository.save(comment);
    }

    public void saveReply(StuReplyPostDTO stuReplyPostDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Comment comment = commentRepository.findById(stuReplyPostDTO.getCommentId()).get();
        Reply reply = new Reply();
        reply.setComment(comment);
        reply.setCommenterCode(stuReplyPostDTO.getCommenterCode());
        reply.setDateTime(LocalDateTime.now().format(dtf));
        reply.setNotification(true);
        reply.setText(stuReplyPostDTO.getText());
        replyRepository.save(reply);

    }

    public Comment getCommentById(int commentId){
        return commentRepository.findById(commentId).get();
    }

}
