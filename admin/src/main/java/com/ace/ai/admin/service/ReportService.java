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
       Batch batch=batchRepository.findBatchById(batchId);
       Course course =courseRepository.findCourseById(batch.getCourse().getId());
       attendanceReportDTO.setBatchName(batch.getName());
       attendanceReportDTO.setCourseName(course.getName());
       List<Classroom> classroomList= classRoomRepository.findAllByBatchIdAndDeleteStatus(batchId,false);
        List<StudentAttendanceDTO> studentDTOList=adminDashboardService.getStuAttendanceByBatch(batchId);
       HashMap<Integer,String> studentAttendance=new HashMap<>();
        HashMap<Integer,String> studentNames=new HashMap<>();
       List<String> dateList=new ArrayList<>();
       if(classroomList!=null){
           for(Classroom c:classroomList){
               attendanceReportDTO.setTeacherName(c.getTeacherName());
           List<Attendance> attendances= c.getAttendances();
           if(attendances!=null){
               for(Attendance a:attendances){
                  if(!a.getStudent().isDeleteStatus()) {
                      studentAttendance.put(a.getStudent().getId(), a.getAttend());
                      studentNames.put(a.getStudent().getId(), a.getStudent().getName());
                  }
               }

           }
               dateList.add(c.getDate());
           }

             attendanceReportDTO.setStudentAndAttend(studentAttendance);
           attendanceReportDTO.setDateList(dateList);
           attendanceReportDTO.setStudentNames(studentNames);
           attendanceReportDTO.setStudentDTOList(studentDTOList);
           return attendanceReportDTO;

       }
       else{
           return attendanceReportDTO;
       }
    }

    public ExamMarkReportDTO getStudentMarks(Integer batchId) {
            ExamMarkReportDTO examMarkReportDTO=new ExamMarkReportDTO();
            List<Student> studentList=studentRepository.findByBatchIdAndDeleteStatus(batchId,false);
            List<BatchExamForm> batchExamForms1=new ArrayList<>();
            Batch batch=batchRepository.findBatchById(batchId);
            List<Student> examPresent=new ArrayList<>();
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
