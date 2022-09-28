package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.*;
import com.ace.ai.admin.dtomodel.*;
import com.ace.ai.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    ClassRoomRepository classRoomRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    BatchExamFormRepository batchExamFormRepository;
    @Autowired
    StudentExamMarkService studentExamMarkService;
    @Autowired
    AdminDashboardService adminDashboardService;
    @Autowired
    StudentAssignmentMarkService studentAssignmentMarkService;


    public AttendanceReportDTO getAttendance(Integer batchId){
       AttendanceReportDTO attendanceReportDTO=new AttendanceReportDTO();
       List<Student> studentList=studentRepository.findByBatchIdAndDeleteStatus(batchId,false);
        List<StudentAttendanceDTO> studentDTOList=adminDashboardService.getStuAttendanceByBatch(batchId);
        Batch batch=batchRepository.findBatchById(batchId);
        attendanceReportDTO.setBatchName(batch.getName());
       attendanceReportDTO.setCourseName(batch.getCourse().getName());
       List<AttendanceDTO> attendanceDTOList=attendanceService.getAllAttendanceList(batchId);
       attendanceReportDTO.setAttendanceDTOS(attendanceDTOList);
       attendanceReportDTO.setStudents(studentList);
        attendanceReportDTO.setStudentDTOList(studentDTOList);
       return attendanceReportDTO;
    }

    public ExamMarkReportDTO getStudentMarks(Integer batchId) {
            ExamMarkReportDTO examMarkReportDTO=new ExamMarkReportDTO();
            List<Student> studentList=studentRepository.findByBatchIdAndDeleteStatus(batchId,false);
            Batch batch=batchRepository.findBatchById(batchId);
            examMarkReportDTO.setBatchName(batch.getName());
            examMarkReportDTO.setCourseName(batch.getCourse().getName());
            List<ExamMarkDTO> examMarkDTOList=studentExamMarkService.getExamMarkDTOList(batchId);
            examMarkReportDTO.setExamMarkDTOList(examMarkDTOList);
            examMarkReportDTO.setStudents(studentList);
            return examMarkReportDTO;
    }
    public AssignmentReportDTO getAssigmentMarks(Integer batchId){
        AssignmentReportDTO assignmentReportDTO=new AssignmentReportDTO();
      List<AssignmentMarkDTO> assignmentMarkDTOList= studentAssignmentMarkService.getAssignmentMarkDTOList(batchId);
      List<Student> studentList=studentRepository.findByBatchIdAndDeleteStatus(batchId,false);
        Batch batch=batchRepository.findBatchById(batchId);
     assignmentReportDTO.setBatchName(batch.getName());
     assignmentReportDTO.setCourseName(batch.getCourse().getName());
      assignmentReportDTO.setStudentAssignmentMarks(assignmentMarkDTOList);
      assignmentReportDTO.setStudentList(studentList);
      return assignmentReportDTO;
    }
}
