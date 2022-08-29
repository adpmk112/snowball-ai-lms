package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.repository.AttendanceRepository;
import com.ace.ai.admin.repository.ClassRoomRepository;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.dtomodel.AttendanceDTO;

@Slf4j
@Service
public class AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    ClassRoomRepository classRoomRepository;

    public List<AttendanceDTO> showAttendanceTable(Integer batchId){
        
        List<AttendanceDTO>attendanceDTOList = new ArrayList<>();

        List<Attendance> attendanceList = new ArrayList<>();

        List<Classroom> classroomList = classRoomRepository.findIdByBatchId(batchId);

        for (Classroom classroom : classroomList) {

            attendanceList = attendanceRepository.findAllByClassroomId(classroom.getId());

            for (Attendance attendance : attendanceList) {

                AttendanceDTO attendanceDTO = new AttendanceDTO();

                log.info("" + classroom.getId());
                log.info(classroom.getDate());

                attendanceDTO.setStudentName(attendance.getStudent().getName());
                log.info(attendanceDTO.getStudentName());

                attendanceDTO.setAttendStatus(attendance.getAttend());
                log.info(attendanceDTO.getAttendStatus());

                attendanceDTOList.add(attendanceDTO);
            }

        }
        
       /*  log.info(attendanceDTOList.get(2).getDate() + " "
                + attendanceDTOList.get(2).getStudentName() + " "
                + attendanceDTOList.get(2).getAttendStatus());
        */

        return attendanceDTOList;
    }

    public List<AttendanceDTO> getClassroomDate(Integer batchId){

        List<Classroom> classroomDateList = classRoomRepository.findDateByBatchId(batchId);

        List<AttendanceDTO>attendanceDTOListForDate = new ArrayList<>();

        for(Classroom classroom : classroomDateList){

            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            AttendanceDTO attendanceDTO = new AttendanceDTO();

            attendanceDTO.setDate(LocalDate.parse(classroom.getDate(),df));

            attendanceDTOListForDate.add(attendanceDTO);
        }

        return attendanceDTOListForDate;
    }

}
