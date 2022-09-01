package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.repository.AttendanceRepository;
import com.ace.ai.admin.repository.ClassRoomRepository;
import com.ace.ai.admin.repository.StudentRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.dtomodel.AttendanceDTO;

@Slf4j
@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    StudentRepository studentRepository;

    public void saveAttendance(Attendance attendance){
        attendanceRepository.save(attendance);
    }

    public List<AttendanceDTO> getAllAttendanceList(){
        List<Attendance> allAttendanceList = attendanceRepository.findAllOrderByIdAsc();

        List<AttendanceDTO> attendanceDTOList = new ArrayList<>();
        List<Student> allStudent = studentRepository.findAllOrderByIdAsc();

        for(Attendance attendance : allAttendanceList){
            int classroomId = attendance.getClassroom().getId();
            String date = attendance.getClassroom().getDate();

            HashMap<Integer,String> studentAndAttend = new HashMap<>();

            AttendanceDTO attendanceDTO = new AttendanceDTO();
        }




        return new ArrayList<>();
    }

}
