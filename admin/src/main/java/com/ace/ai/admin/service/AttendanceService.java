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

    public List<Student> getAllStudentByDeleteStatus(int batchId){
        return studentRepository.findByDeleteStatusAndBatchIdOrderByIdAsc(false, batchId);
    }

    public List<AttendanceDTO> getAllAttendanceList(int batchId){
        List<Classroom> allClassroomList = classRoomRepository.findAllByDeleteStatusAndBatchIdOrderByIdAsc(false, batchId);

        List<AttendanceDTO> attendanceDTOList = new ArrayList<>();
        List<Student> allStudent = getAllStudentByDeleteStatus(batchId);// All student according to batch
        //get attendance dto
        for(Classroom classroom : allClassroomList){
            int classroomId = classroom.getId();
            String date = classroom.getDate();

            HashMap<Integer,String> studentAndAttend = new HashMap<>();
            //get hashmap list
            for(Student student: allStudent){                
                Attendance attendance = attendanceRepository.findByStudentIdAndClassroomId(student.getId(), classroomId);
                if(attendance != null){
                    studentAndAttend.put(student.getId(), attendance.getAttend());
                }
                //add to list
            }
            AttendanceDTO attendanceDTO = new AttendanceDTO(date, classroomId, studentAndAttend);
            attendanceDTOList.add(attendanceDTO);
        }

        return attendanceDTOList;
    }

    public Attendance getByStudentIdAndClassroomId(int studentId,int classroomId){
        return attendanceRepository.findByStudentIdAndClassroomId(studentId, classroomId);
    }
    

}
