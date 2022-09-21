package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.dtomodel.AttendanceReportDTO;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.ClassRoomRepository;
import com.ace.ai.admin.repository.CourseRepository;
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

    public AttendanceReportDTO getAttendance(Integer batchId){
        AttendanceReportDTO attendanceReportDTO=new AttendanceReportDTO();
       Batch batch=batchRepository.findBatchById(batchId);
       Course course =courseRepository.findCourseById(batch.getCourse().getId());
       attendanceReportDTO.setBatchName(batch.getName());
       attendanceReportDTO.setCourseName(course.getName());
       List<Classroom> classroomList= classRoomRepository.findAllByBatchIdAndDeleteStatus(batchId,false);
       HashMap<Integer,String> studentAttendance=new HashMap<>();
        HashMap<Integer,String> studentNames=new HashMap<>();
       List<String> dateList=new ArrayList<>();
       if(classroomList!=null){
           for(Classroom c:classroomList){
               attendanceReportDTO.setTeacherName(c.getTeacherName());
           List<Attendance> attendances= c.getAttendances();
           if(attendances!=null){
               for(Attendance a:attendances){
                    studentAttendance.put(a.getStudent().getId(),a.getAttend());
                   studentNames.put(a.getStudent().getId(),a.getStudent().getName());
               }

           }
               dateList.add(c.getDate());
           }

             attendanceReportDTO.setStudentAndAttend(studentAttendance);
           attendanceReportDTO.setDateList(dateList);
           attendanceReportDTO.setStudentNames(studentNames);
           return attendanceReportDTO;

       }
       else{
           return attendanceReportDTO;
       }
    }
}
