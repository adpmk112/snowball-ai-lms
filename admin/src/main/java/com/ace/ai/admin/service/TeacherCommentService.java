package com.ace.ai.admin.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterBatch;
import com.ace.ai.admin.datamodel.ChapterFile;
import com.ace.ai.admin.datamodel.Comment;
import com.ace.ai.admin.datamodel.CustomChapter;
import com.ace.ai.admin.datamodel.CustomChapterFile;
import com.ace.ai.admin.datamodel.Reply;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.StudentAssignmentMark;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.dtomodel.AssignmentDateTimeDTO;
import com.ace.ai.admin.dtomodel.AssignmentListCommentDTO;
import com.ace.ai.admin.dtomodel.AssignmentMarkCommentDTO;
import com.ace.ai.admin.dtomodel.AssignmentMarkDTO;
import com.ace.ai.admin.dtomodel.ChapterFileDTO;
import com.ace.ai.admin.dtomodel.ChapterListCommentDTO;
import com.ace.ai.admin.dtomodel.CommentLocationDTO;
import com.ace.ai.admin.dtomodel.StudentAssignmentCommentDTO;
import com.ace.ai.admin.dtomodel.TeacherAssignmentViewDTO;
import com.ace.ai.admin.dtomodel.TeacherCommentPostDTO;
import com.ace.ai.admin.dtomodel.TeacherCommentViewDTO;
import com.ace.ai.admin.dtomodel.TeacherReplyPostDTO;
import com.ace.ai.admin.dtomodel.TeacherReplyViewDTO;
import com.ace.ai.admin.dtomodel.VideoListCommentDTO;
import com.ace.ai.admin.repository.AssignmentRepository;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.ChapterBatchRepository;
import com.ace.ai.admin.repository.ChapterFileRepository;
import com.ace.ai.admin.repository.ChapterRepository;
import com.ace.ai.admin.repository.CommentRepository;
import com.ace.ai.admin.repository.CustomChapterFileRepository;
import com.ace.ai.admin.repository.CustomChapterRepository;
import com.ace.ai.admin.repository.ReplyRepository;
import com.ace.ai.admin.repository.StudentAssignmentMarkRepository;
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
    @Autowired
    ChapterBatchRepository chapterBatchRepository;
    @Autowired
    CustomChapterRepository customChapterRepository;
    @Autowired
    CustomChapterFileRepository customChapterFileRepository;
    @Autowired
    ChapterFileRepository chapterFileRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    StudentAssignmentMarkRepository studentAssignmentMarkRepository;


    public Assignment findAssignmentById(int id){
        return assignmentRepository.findById(id).get();
    }
    public ChapterFile findChapterFileById(int chapterFileId){
        return chapterFileRepository.findById(chapterFileId);
    }

    public CustomChapterFile findCustomChapterFileById(int customChapterId){
        return customChapterFileRepository.findById(customChapterId).get();
    }

    public Chapter findChapterById(int chapterId){
        return chapterRepository.findById(chapterId).get();
    }
    public CustomChapter findCustomChapterById(int customChapterId){
        return customChapterRepository.findById(customChapterId).get();
    }

    public List<CommentLocationDTO> getCommentLocation(int batchId) {
        List<Comment> commentList = commentRepository.findByDeleteStatusAndBatchId(false, batchId);
        List<CommentLocationDTO> commentLocationDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
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

    public Teacher getTeacherByCode(String code) {
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
                String teacherName = "";
                String stuPhoto = "";
                String teacherPhoto = "";

                if (studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(), false) != null) {
                    stuName = studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(), false).getName();
                    stuPhoto = studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(), false).getPhoto();
                } else if (teacherRepository.findByCode(reply.getCommenterCode()) != null) {
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
            String teacherPhoto = "";
            if (studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(), false) != null) {
                stuName = studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(), false).getName();
                stuPhoto = studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(), false).getPhoto();
            } else if (teacherRepository.findByCode(comment.getCommenterCode()) != null) {
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
            teacherCommentViewDTO.setTeacherReplayViewDTOList(teacherReplyViewDTOList);
            teacherCommentViewDTOList.add(teacherCommentViewDTO);
        }
        return teacherCommentViewDTOList;
    }

    public List<TeacherCommentViewDTO> getCommentListByBatchIdAndLocationAndCommenterCode(
            TeacherAssignmentViewDTO teacherAssignmentViewDTO) {
        List<TeacherCommentViewDTO> teacherCommentViewDTOList = new ArrayList<>();
        List<Comment> commentList = new ArrayList<>();
        Assignment assignment = assignmentRepository.findById(teacherAssignmentViewDTO.getAssignmentId()).get();
        String location = assignment.getName()+teacherAssignmentViewDTO.getStuCode();
        System.out.println("location ====" + location);
        List<Comment> stuCodeCommentList = commentRepository.findByBatchIdAndLocationAndCommenterCode(
                teacherAssignmentViewDTO.getBatchId(), location, teacherAssignmentViewDTO.getStuCode());
        for (Comment stuCodeComment : stuCodeCommentList) {
            commentList.add(stuCodeComment);
        }

        for (String teacherCode : teacherAssignmentViewDTO.getTeacherCode()) {
            List<Comment> teacherCommentList = commentRepository.findByBatchIdAndLocationAndCommenterCode(
                    teacherAssignmentViewDTO.getBatchId(), location, teacherCode);
            for (Comment teacherComment : teacherCommentList) {
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
                String teacherName = "";
                String stuPhoto = "";
                String teacherPhoto = "";

                if (studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(), false) != null) {
                    stuName = studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(), false).getName();
                    stuPhoto = studentRepository.findByCodeAndDeleteStatus(reply.getCommenterCode(), false).getPhoto();
                } else if (teacherRepository.findByCode(reply.getCommenterCode()) != null) {
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
            String teacherPhoto = "";
            if (studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(), false) != null) {
                stuName = studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(), false).getName();
                stuPhoto = studentRepository.findByCodeAndDeleteStatus(comment.getCommenterCode(), false).getPhoto();
            } else if (teacherRepository.findByCode(comment.getCommenterCode()) != null) {
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
            teacherCommentViewDTO.setTeacherReplayViewDTOList(teacherReplyViewDTOList);
            teacherCommentViewDTOList.add(teacherCommentViewDTO);
        }
        return teacherCommentViewDTOList;
    }

    public List<String> getTeacherCodeListByBatchId(int batchId) {
        List<TeacherBatch> teacherBatchList = teacherBatchRepository.findByBatchId(batchId);
        List<String> teacherList = new ArrayList<>();
        for (TeacherBatch teacherBatch : teacherBatchList) {
            teacherList.add(teacherBatch.getTeacher().getCode());

        }
        return teacherList;
    }

    public List<AssignmentListCommentDTO> getAssignmentListByBatchId(int batchId) {
        List<Assignment> assignmentList = assignmentRepository.findByDeleteStatusAndBatchId(false, batchId);
        List<AssignmentListCommentDTO> assignmentListCommentDTOList = new ArrayList<>();
        List<Student> studentList = studentRepository.findByBatchIdAndDeleteStatus(batchId, false);
        for (Assignment assignment : assignmentList) {
            AssignmentListCommentDTO assignmentListCommentDTO = new AssignmentListCommentDTO();

            List<StudentAssignmentCommentDTO> studentAssignmentCommentDTOList = new ArrayList<>();
            for (Student student : studentList) {
                StudentAssignmentCommentDTO studentAssignmentCommentDTO = new StudentAssignmentCommentDTO();

                boolean noti = false;
                List<Comment> commentList = commentRepository.findByBatchIdAndLocationAndCommenterCode(batchId,
                        assignment.getName(), student.getCode());
                for (Comment comment : commentList) {
                    if (comment.isNotification() == true) {
                        noti = true;
                    }
                }
                studentAssignmentCommentDTO.setStuId(student.getId());
                studentAssignmentCommentDTO.setStuName(student.getName());
                studentAssignmentCommentDTO.setPhoto(student.getPhoto());
                studentAssignmentCommentDTO.setStuCode(student.getCode());
                studentAssignmentCommentDTO.setNotification(noti);
                studentAssignmentCommentDTOList.add(studentAssignmentCommentDTO);

            }
            assignmentListCommentDTO.setAssignmentId(assignment.getId());
            assignmentListCommentDTO.setAssignmentName(assignment.getName());
            assignmentListCommentDTO.setStudentAssignmentCommentDTOList(studentAssignmentCommentDTOList);
            assignmentListCommentDTOList.add(assignmentListCommentDTO);
        }
        return assignmentListCommentDTOList;
    }

    public List<ChapterListCommentDTO> getChapterListAndCustomChapterListByBatchId(int batchId) {
        List<ChapterBatch> chapterBatchList = chapterBatchRepository.findByBatchIdAndDeleteStatus(batchId,0);
        List<ChapterListCommentDTO> chapterListCommentDTOList = new ArrayList<>();
        List<CustomChapter> customChapterList = customChapterRepository.findByBatchIdAndDeleteStatus(batchId, false);
        for (ChapterBatch chapterBatch : chapterBatchList) {
            ChapterListCommentDTO chapterListCommentDTO = new ChapterListCommentDTO();

            
            chapterListCommentDTO.setChapterId(chapterBatch.getChapter().getId());
            chapterListCommentDTO.setChapterName(chapterBatch.getChapter().getName());
            chapterListCommentDTO.setChapterType("chapter");
            chapterListCommentDTOList.add(chapterListCommentDTO);
        }
        for(CustomChapter customChapter : customChapterList){
            ChapterListCommentDTO chapterListCommentDTO = new ChapterListCommentDTO();

            
            chapterListCommentDTO.setChapterId(customChapter.getId());
            chapterListCommentDTO.setChapterName(customChapter.getName());
            chapterListCommentDTO.setChapterType("customChapter");
            chapterListCommentDTOList.add(chapterListCommentDTO);
        }
        return chapterListCommentDTOList;
    }

    

    public List<VideoListCommentDTO> getVideoListByBatchId(int batchId){
        List<ChapterBatch> chapterBatchList = chapterBatchRepository.findByBatchIdAndDeleteStatus(batchId,0);
        List<CustomChapter> customChapterList = customChapterRepository.findByBatchIdAndDeleteStatus(batchId, false);
        List<VideoListCommentDTO> videoListCommentDTOList = new ArrayList<>();
        for(ChapterBatch chapterBatch : chapterBatchList){
            List<ChapterFile> chapterFileList = chapterFileRepository.findByChapterIdAndFileTypeAndDeleteStatus(chapterBatch.getChapter().getId(),"video",0);
            for(ChapterFile chapterFile: chapterFileList){
                VideoListCommentDTO videoListCommentDTO = new VideoListCommentDTO();
                videoListCommentDTO.setVideoId(chapterFile.getId());
                videoListCommentDTO.setVideoName(chapterFile.getName());
                videoListCommentDTO.setChapterId(chapterFile.getChapter().getId());
                videoListCommentDTO.setChapterFileType("chapterFile");
                videoListCommentDTOList.add(videoListCommentDTO);
            }
        }
        for(CustomChapter customChapter : customChapterList){
            List<CustomChapterFile> customChapterFileList = customChapterFileRepository.findByCustomChapterIdAndFileTypeAndDeleteStatus(customChapter.getId(),"video",false);
            for(CustomChapterFile customChapterFile: customChapterFileList){
                VideoListCommentDTO videoListCommentDTO = new VideoListCommentDTO();
                videoListCommentDTO.setVideoId(customChapterFile.getId());
                videoListCommentDTO.setVideoName(customChapterFile.getName());
                videoListCommentDTO.setChapterId(customChapterFile.getCustomChapter().getId());
                videoListCommentDTO.setChapterFileType("customChapterFile");
                videoListCommentDTOList.add(videoListCommentDTO);
            }
        }
        return videoListCommentDTOList;
    }

    public void saveComment(TeacherCommentPostDTO teacherCommentPostDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Batch batch = batchRepository.findById(teacherCommentPostDTO.getBatchId()).get();

        Comment comment = new Comment();
        comment.setCommenterCode(teacherCommentPostDTO.getCommenterCode());
        comment.setDateTime(LocalDateTime.now().format(dtf));
        // location need to set in controller
        comment.setLocation(teacherCommentPostDTO.getLocation());
        comment.setNotification(true);
        comment.setText(teacherCommentPostDTO.getText());
        comment.setBatch(batch);
        commentRepository.save(comment);
    }

    public void saveReply(TeacherReplyPostDTO teacherReplyPostDTO) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        Comment comment = commentRepository.findById(teacherReplyPostDTO.getCommentId()).get();
        Reply reply = new Reply();
        reply.setComment(comment);
        reply.setCommenterCode(teacherReplyPostDTO.getCommenterCode());
        reply.setDateTime(LocalDateTime.now().format(dtf));
        reply.setNotification(true);
        reply.setText(teacherReplyPostDTO.getText());
        replyRepository.save(reply);

    }

    public AssignmentMarkCommentDTO getStudentMarkByAssiIdAndStuId(int assignmentId, int studentId) throws ParseException{
        int studentMark;
        String submitDate;
        String submitTime;
        AssignmentMarkCommentDTO assignmentMarkCommentDTO = new AssignmentMarkCommentDTO();
        StudentAssignmentMark studentAssignmentMark =studentAssignmentMarkRepository.findByAssignment_IdAndStudent_Id(assignmentId , studentId);
        if(studentAssignmentMark != null){
            if(studentAssignmentMark.getStudentMark() == 0 && !studentAssignmentMark.getDate().isBlank() && !studentAssignmentMark.getTime().isBlank()){
                studentMark = 100;
                submitDate = studentAssignmentMark.getDate();
                submitTime = twelveHourFormat(studentAssignmentMark.getTime());

            }
            else{
                studentMark = studentAssignmentMark.getStudentMark();
                submitDate = studentAssignmentMark.getDate();
                submitTime = twelveHourFormat(studentAssignmentMark.getTime());
            }
        }
        else{
            studentMark =100;
            submitDate = "SubmitDate";
            submitTime = "SubmitTime";
        }
        assignmentMarkCommentDTO.setStudentMark(studentMark);
        assignmentMarkCommentDTO.setSubmitDate(submitDate);
        assignmentMarkCommentDTO.setSubmitTime(submitTime);
        return assignmentMarkCommentDTO;   
    }

    public String twelveHourFormat(String time) throws ParseException {

        final SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
        final Date dateObj = sdf.parse(time);
        return new SimpleDateFormat("hh:mm a").format(dateObj);
    }

    public AssignmentDateTimeDTO getDateTimeByAssignmentId(int assignmentId) throws ParseException{
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        AssignmentDateTimeDTO assignmentDateTimeDTO = new AssignmentDateTimeDTO();
        assignmentDateTimeDTO.setFileName(assignment.getName());
        if(assignment != null){
        assignmentDateTimeDTO.setEnd_date(assignment.getEnd_date());
        assignmentDateTimeDTO.setEnd_time(twelveHourFormat(assignment.getEnd_time()));
        }
        return assignmentDateTimeDTO;
    }

    public Student getStudetByCode(String code){
        return studentRepository.findByCodeAndDeleteStatus(code,false);
    }

    public String getStatusAssignmentId(int assignmentId,int studentId){
        Assignment assignment = assignmentRepository.findById(assignmentId).get();
        StudentAssignmentMark studentAssignmentMark = studentAssignmentMarkRepository.findByAssignment_IdAndStudent_Id(assignmentId,studentId);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.now();
        String currentTime = localTime.format(timeFormatter);
        LocalTime localCurrentTime =LocalTime.parse(currentTime,timeFormatter);
        String status =null;
        LocalDate dueDate,submitDate;
        LocalTime dueTime,submitTime;
        if(assignment != null && studentAssignmentMark == null){
            if(assignment.getEnd_date() != null && assignment.getEnd_time() != null){
                dueDate = LocalDate.parse(assignment.getEnd_date(), dateFormatter);
                dueTime = LocalTime.parse(assignment.getEnd_time(), timeFormatter);            
               if(dueDate.isBefore(LocalDate.now()) == false){                
                       if(dueTime.compareTo(localCurrentTime) == 0 ){
                           status = "early2";
                       }
                       
                       else if(dueTime.isBefore(localCurrentTime) == true ){
                           status = "late2";
                       }
                       else if(dueTime.isBefore(localCurrentTime) == false)
                       status = "early1";
                       
               }    
               else if(dueDate.isBefore(LocalDate.now()) == true){
                   status = "late1";
               } 
           }   
        }
        else if(assignment != null && studentAssignmentMark != null){
            
                if(assignment.getEnd_date() != null && assignment.getEnd_time() != null && studentAssignmentMark.getDate() != null && studentAssignmentMark.getTime() != null){
                    dueDate = LocalDate.parse(assignment.getEnd_date(), dateFormatter);
                    dueTime = LocalTime.parse(assignment.getEnd_time(), timeFormatter); 
                    submitDate = LocalDate.parse(studentAssignmentMark.getDate(), dateFormatter);
                    submitTime = LocalTime.parse(studentAssignmentMark.getTime(), timeFormatter);            
                   if(dueDate.isBefore(submitDate) == false){                
                           if(dueTime.compareTo(submitTime) == 0 ){
                               status = "early2";
                           }
                           
                           else if(dueTime.isBefore(submitTime) == true ){
                               status = "late2";
                           }
                           else if(dueTime.isBefore(submitTime) == false)
                           status = "early1";
                           
                   }    
                   else if(dueDate.isBefore(submitDate) == true){
                       status = "late1";
                   } 
               }   
            
        }
        return status;
    }
}
