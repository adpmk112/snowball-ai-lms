package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.dtomodel.StudentAttendanceDTO;
import com.ace.ai.admin.repository.AttendanceRepository;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.StudentRepository;



@Service
public class AdminDashboardService{
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    AttendanceService attendanceService;


    public List<StudentAttendanceDTO> getStuAttendanceByBatch(int batchId){
       
            //this is student's all attendance list
            List<StudentAttendanceDTO> studentAttendanceDTOList = new ArrayList<>();
            
            List<Student> studentList = studentRepository.findByDeleteStatusAndBatchId(false,batchId);
           for(Student student : studentList){
            //this is student one attendance
            StudentAttendanceDTO adminDashboardDTO = new StudentAttendanceDTO();
     
            int totalDays = attendanceRepository.findByStudentId(student.getId()).size();
            int attendDays = attendanceRepository.findByAttendAndStudentId("present", student.getId()).size();
            System.out.println(attendDays);
            if(totalDays==0){

            }else{
            int attendPercentage = (attendDays*100)/totalDays;

            adminDashboardDTO.setBatchId(batchId);
            adminDashboardDTO.setStuId(student.getId());
            adminDashboardDTO.setStuName(student.getName());
            adminDashboardDTO.setAttendance(attendPercentage);
            //add one attandance to attandance list with student name
            studentAttendanceDTOList.add(adminDashboardDTO);
           }
           }
       return studentAttendanceDTOList;
    }
 
 }

