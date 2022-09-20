package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Comment;
import com.ace.ai.admin.datamodel.Reply;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.dtomodel.CommentLocationDTO;
import com.ace.ai.admin.dtomodel.TeacherAssignmentViewDTO;
import com.ace.ai.admin.dtomodel.TeacherCommentViewDTO;
import com.ace.ai.admin.dtomodel.TeacherReplyViewDTO;
import com.ace.ai.admin.repository.AssignmentRepository;
import com.ace.ai.admin.repository.CommentRepository;
import com.ace.ai.admin.repository.ReplyRepository;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.repository.TeacherBatchRepository;
import com.ace.ai.admin.repository.TeacherRepository;

@Service
public class TeacherCommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ReplyRepository replyRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    TeacherBatchRepository teacherBatchRepository;

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

    public Teacher getTeacherByCode(String code){
        return teacherRepository.findTeacherByCode(code);
    }

    public List<TeacherCommentViewDTO> getCommentListByBatchIdAndLocation(int batchId, String location) {
        List<TeacherCommentViewDTO> teacherCommentViewDTOList = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByBatchIdAndLocation(batchId, location);
        for (Comment comment : commentList) {
            TeacherCommentViewDTO teacherCommentViewDTO = new TeacherCommentViewDTO();
            List<Reply> replyList = replyRepository.findByCommentId(comment.getId());
            List<TeacherReplyViewDTO> teacherReplyViewDTOList = new ArrayList<>();
            for (Reply reply : replyList) {
                TeacherReplyViewDTO teacherReplyViewDTO = new TeacherReplyViewDTO();
                teacherReplyViewDTO.setId(reply.getId());
                teacherReplyViewDTO.setText(reply.getText());
                teacherReplyViewDTO.setDateTime(reply.getDateTime());
                teacherReplyViewDTO.setCommentId(reply.getComment().getId());
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
                    teacherReplyViewDTO.setCommenterName(teacherName);
                    teacherReplyViewDTO.setCommenterPhoto(teacherPhoto);
                } else if (teacherName.isBlank()) {
                    teacherReplyViewDTO.setCommenterName(stuName);
                    teacherReplyViewDTO.setCommenterPhoto(stuPhoto);
                }

                teacherReplyViewDTO.setCommenterCode(reply.getCommenterCode());
                teacherReplyViewDTO.setDeleteStatus(reply.isDeleteStatus());
                teacherReplyViewDTO.setNotification(reply.isNotification());
                teacherReplyViewDTOList.add(teacherReplyViewDTO);

            }
            teacherCommentViewDTO.setId(comment.getId());
            teacherCommentViewDTO.setText(comment.getText());
            teacherCommentViewDTO.setLocation(comment.getLocation());
            teacherCommentViewDTO.setDateTime(comment.getDateTime());
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
                teacherCommentViewDTO.setCommenterName(teacherName);
                teacherCommentViewDTO.setCommenterPhoto(teacherPhoto);
            } else if (teacherName.isBlank()) {
                teacherCommentViewDTO.setCommenterName(stuName);
                teacherCommentViewDTO.setCommenterPhoto(stuPhoto);
                
            }
            teacherCommentViewDTO.setCommenterCode(comment.getCommenterCode());
            teacherCommentViewDTO.setNotification(comment.isNotification());
            teacherCommentViewDTO.setStuReplayViewDTOList(teacherReplyViewDTOList);
            teacherCommentViewDTOList.add(teacherCommentViewDTO);
        }
        return teacherCommentViewDTOList;
    }


    public List<TeacherCommentViewDTO> getCommentListByBatchIdAndLocationAndCommenterCode(TeacherAssignmentViewDTO teacherAssignmentViewDTO) {
        List<TeacherCommentViewDTO> teacherCommentViewDTOList = new ArrayList<>();
        List<Comment> commentList = new ArrayList<>();
        Assignment assignment = assignmentRepository.findById(teacherAssignmentViewDTO.getAssignmentId()).get();
        List<Comment> stuCodeCommentList = commentRepository.findByBatchIdAndLocationAndCommenterCode(teacherAssignmentViewDTO.getBatchId(), assignment.getName(), teacherAssignmentViewDTO.getStuCode());
        for(Comment stuCodeComment : stuCodeCommentList){
            commentList.add(stuCodeComment);
        }

        for(String teacherCode : teacherAssignmentViewDTO.getTeacherCode()){
            List<Comment> teacherCommentList = commentRepository.findByBatchIdAndLocationAndCommenterCode(teacherAssignmentViewDTO.getBatchId(), assignment.getName(), teacherCode);
            for(Comment teacherComment : teacherCommentList){
                commentList.add(teacherComment);
            }
        
        }
        
        
        
        for (Comment comment : commentList) {
            TeacherCommentViewDTO teacherCommentViewDTO = new TeacherCommentViewDTO();
            List<Reply> replyList = replyRepository.findByCommentId(comment.getId());
            List<TeacherReplyViewDTO> teacherReplyViewDTOList = new ArrayList<>();
            for (Reply reply : replyList) {
                TeacherReplyViewDTO teacherReplyViewDTO = new TeacherReplyViewDTO();
                teacherReplyViewDTO.setId(reply.getId());
                teacherReplyViewDTO.setText(reply.getText());
                teacherReplyViewDTO.setDateTime(reply.getDateTime());
                teacherReplyViewDTO.setCommentId(reply.getComment().getId());
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
                    teacherReplyViewDTO.setCommenterName(teacherName);
                    teacherReplyViewDTO.setCommenterPhoto(teacherPhoto);
                } else if (teacherName.isBlank()) {
                    teacherReplyViewDTO.setCommenterName(stuName);
                    teacherReplyViewDTO.setCommenterPhoto(stuPhoto);
                }

                teacherReplyViewDTO.setCommenterCode(reply.getCommenterCode());
                teacherReplyViewDTO.setDeleteStatus(reply.isDeleteStatus());
                teacherReplyViewDTO.setNotification(reply.isNotification());
                teacherReplyViewDTOList.add(teacherReplyViewDTO);

            }
            teacherCommentViewDTO.setId(comment.getId());
            teacherCommentViewDTO.setText(comment.getText());
            teacherCommentViewDTO.setLocation(comment.getLocation());
            teacherCommentViewDTO.setDateTime(comment.getDateTime());
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
                teacherCommentViewDTO.setCommenterName(teacherName);
                teacherCommentViewDTO.setCommenterPhoto(teacherPhoto);
            } else if (teacherName.isBlank()) {
                teacherCommentViewDTO.setCommenterName(stuName);
                teacherCommentViewDTO.setCommenterPhoto(stuPhoto);
                
            }
            teacherCommentViewDTO.setCommenterCode(comment.getCommenterCode());
            teacherCommentViewDTO.setNotification(comment.isNotification());
            teacherCommentViewDTO.setStuReplayViewDTOList(teacherReplyViewDTOList);
            teacherCommentViewDTOList.add(teacherCommentViewDTO);
        }
        return teacherCommentViewDTOList;
    }

    public List<String> getTeacherCodeListByBatchId(int batchId){
        List<TeacherBatch> teacherBatchList = teacherBatchRepository.findByBatchId(batchId);
        List<String> teacherList = new ArrayList<>();
        for(TeacherBatch teacherBatch : teacherBatchList){
            teacherList.add(teacherBatch.getTeacher().getCode());

        }
        return teacherList;
    }


   
}
