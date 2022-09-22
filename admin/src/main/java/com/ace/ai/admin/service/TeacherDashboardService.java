package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.datamodel.Comment;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.StudentAssignmentMark;
import com.ace.ai.admin.datamodel.StudentExamMark;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.dtomodel.StudentExamMarkDTO;
import com.ace.ai.admin.dtomodel.TeaceherDashboardAssignmentDTO;
import com.ace.ai.admin.dtomodel.TeacherCommentDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardAssignmentStudentMarksDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardChartDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardExamDTO;
import com.ace.ai.admin.repository.AssignmentRepository;
import com.ace.ai.admin.repository.AttendanceRepository;
import com.ace.ai.admin.repository.BatchExamFormRepository;
import com.ace.ai.admin.repository.CommentRepository;
import com.ace.ai.admin.repository.StudentAssignmentMarkRepository;
import com.ace.ai.admin.repository.StudentExamMarkRepository;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.repository.TeacherBatchRepository;
import com.ace.ai.admin.repository.TeacherDashboardRepository;
import com.ace.ai.admin.repository.TeacherRepository;

@Service
public class TeacherDashboardService {

    @Autowired
    TeacherDashboardRepository teacherDashboardRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    TeacherBatchRepository teacherBatchRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    StudentExamMarkRepository studentExamMarkRepository;
    @Autowired
    BatchExamFormRepository batchExamFormRepository;
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    StudentAssignmentMarkRepository studentAssignmentMarkRepository;

    public List<Batch> findBatchesByTeacherCode(String teacherCode) {
        Teacher teacher = teacherRepository.findTeacherByCode(teacherCode);
        List<TeacherBatch> teacherBatches = teacher.getTeacherBatches();
        List<Batch> batchList = new ArrayList<>();
        for (TeacherBatch tb : teacherBatches) {
            if (!tb.isDeleteStatus()) {
                batchList.add(tb.getBatch());
            }
        }
        return batchList;
    }

    public List<TeacherDashboardDTO> getBatchNameAndStuCountByTeacherCode(String teacherCode) {
        Teacher teacher = teacherRepository.findByCode(teacherCode);
        List<TeacherBatch> teacherBatchList = teacherBatchRepository.findByTeacherIdAndDeleteStatus(teacher.getId(),
                false);
        List<TeacherDashboardDTO> teacherDashboardDTOList = new ArrayList<>();
        for (TeacherBatch teacherBatch : teacherBatchList) {
            TeacherDashboardDTO teacherDashboardDTO = new TeacherDashboardDTO();
            teacherDashboardDTO.setBatchId(teacherBatch.getBatch().getId());
            teacherDashboardDTO.setBatchName(teacherBatch.getBatch().getName());
            int studentCount = studentRepository.findByDeleteStatusAndBatchId(false, teacherBatch.getBatch().getId())
                    .size();
            teacherDashboardDTO.setStudentCount(studentCount);
            teacherDashboardDTOList.add(teacherDashboardDTO);
        }
        return teacherDashboardDTOList;
    }

    public List<TeacherDashboardChartDTO> findStudentByBatchId(int batchId) {
        List<Student> studentList = studentRepository.findByDeleteStatusAndBatchId(false, batchId);
        List<TeacherDashboardChartDTO> teacherDashboardChartDTOlist = new ArrayList<>();
        for (Student student : studentList) {
            TeacherDashboardChartDTO teacherDashboardChartDTO = new TeacherDashboardChartDTO();
            int totalDays = attendanceRepository.findByStudentId(student.getId()).size();
            int attendDays = attendanceRepository.findByAttendAndStudentId("present", student.getId()).size();
            int attendPercentage;
            if (totalDays == 0) {
                attendPercentage = 0;
            } else {
                attendPercentage = (totalDays * 100) % attendDays;
            }
            teacherDashboardChartDTO.setBatchId(batchId);
            teacherDashboardChartDTO.setStudentName(student.getName());
            teacherDashboardChartDTO.setAttendance(attendPercentage);
            teacherDashboardChartDTOlist.add(teacherDashboardChartDTO);
        }
        return teacherDashboardChartDTOlist;
    }

    public List<TeacherDashboardExamDTO> getStudentNameAndExamMarkByBatchId(int batchId) {
        List<TeacherDashboardExamDTO> teacherDashboardExamDTOList = new ArrayList<>();
        List<BatchExamForm> batchExamFormList = batchExamFormRepository.findByDeleteStatusAndBatchId(false, batchId);
        if (batchExamFormList != null) {
            for (BatchExamForm batchExamForm : batchExamFormList) {
                TeacherDashboardExamDTO teacherDashboardExamDTO = new TeacherDashboardExamDTO();
                teacherDashboardExamDTO.setBatchId(batchId);
                teacherDashboardExamDTO.setExamForm_id(batchExamForm.getExamForm().getId());
                teacherDashboardExamDTO.setExamForm_name(batchExamForm.getExamForm().getName());
                teacherDashboardExamDTO.setMax_marks(batchExamForm.getExamForm().getMaxMark());
                List<StudentExamMark> studentExamMarkList = studentExamMarkRepository
                        .findByDeleteStatusAndBatchExamFormId(false,
                                batchExamForm.getId());
                List<StudentExamMarkDTO> studentExamMarkDTOList = new ArrayList<>();
                StudentExamMarkDTO studentExamMarkDTO = new StudentExamMarkDTO();
                if (studentExamMarkList != null) {

                    for (StudentExamMark studentExamMark : studentExamMarkList) {
                        studentExamMarkDTO.setStudentMarks(studentExamMark.getStudentMark());
                    }

                    studentExamMarkDTOList.add(studentExamMarkDTO);
                }
                teacherDashboardExamDTO.setStudentExamMarkDTO(studentExamMarkDTOList);
                teacherDashboardExamDTOList.add(teacherDashboardExamDTO);

            }
        }
        return teacherDashboardExamDTOList;

    }

    public List<TeacherCommentDTO> getCommentByBatchId(int batchId) {
        List<Comment> commentList = commentRepository.findByNotificationAndDeleteStatusAndBatchId(true, false, batchId);
        List<TeacherCommentDTO> teacherCommentDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            String commenter_Code = comment.getCommenterCode();
            if (!commenter_Code.isBlank()) {
                List<Student> studentList = studentRepository.findByDeleteStatusAndCodeAndBatchId(false, commenter_Code,
                        batchId);
                for (Student student : studentList) {

                    TeacherCommentDTO teacherCommentDTO = new TeacherCommentDTO();
                    teacherCommentDTO.setBatchId(batchId);
                    teacherCommentDTO.setLocation(comment.getLocation());
                    teacherCommentDTO.setText(comment.getText());
                    teacherCommentDTO.setCommenter_Name(student.getName());
                    teacherCommentDTO.setCommentId(comment.getId());
                    teacherCommentDTOList.add(teacherCommentDTO);

                }
            }
        }
        return teacherCommentDTOList;
    }

    public List<TeaceherDashboardAssignmentDTO> getStuNameAndAssignmentMarksByBatchId(int batchId) {
        List<TeaceherDashboardAssignmentDTO> teaceherDashboardAssignmentDTOList = new ArrayList<>();
        List<Assignment> assignmentList = assignmentRepository.findByDeleteStatusAndBatchId(false, batchId);
        if(assignmentList!=null){
        for (Assignment assignment : assignmentList) {
            TeaceherDashboardAssignmentDTO teaceherDashboardAssignmentDTO = new TeaceherDashboardAssignmentDTO();
            teaceherDashboardAssignmentDTO.setBatchId(batchId);
            teaceherDashboardAssignmentDTO.setAssignmentId(assignment.getId());
            teaceherDashboardAssignmentDTO.setAssignmentName(assignment.getName());
            List<StudentAssignmentMark> studentAssignmentMarkList = studentAssignmentMarkRepository
                    .findByAssignmentId(assignment.getId());
            List<TeacherDashboardAssignmentStudentMarksDTO> teacherDashboardAssignmentStudentMarksDTOList = new ArrayList<>();
            if(studentAssignmentMarkList != null){
                for (StudentAssignmentMark studentAssignmentMark : studentAssignmentMarkList) {
                    TeacherDashboardAssignmentStudentMarksDTO teacherDashboardAssignmentStudentMarksDTO = new TeacherDashboardAssignmentStudentMarksDTO();
                    teacherDashboardAssignmentStudentMarksDTO.setStudentId(studentAssignmentMark.getStudent().getId());
                    teacherDashboardAssignmentStudentMarksDTO
                            .setStudentMarks(String.valueOf(studentAssignmentMark.getStudentMark()));
                    List<Student> studentList = studentRepository.findByDeleteStatusAndIdAndBatchId(false,
                            studentAssignmentMark.getStudent().getId(), batchId);
                    for (Student student : studentList) {

                        teacherDashboardAssignmentStudentMarksDTO.setStudentName(student.getName());
                    }
                    teacherDashboardAssignmentStudentMarksDTOList.add(teacherDashboardAssignmentStudentMarksDTO);
                }
            }
            teaceherDashboardAssignmentDTO
                    .setTeacherDashboardAssignmentStudentMarksDTO(teacherDashboardAssignmentStudentMarksDTOList);
            teaceherDashboardAssignmentDTOList.add(teaceherDashboardAssignmentDTO);
            }
        }
        return teaceherDashboardAssignmentDTOList;
    }

}
