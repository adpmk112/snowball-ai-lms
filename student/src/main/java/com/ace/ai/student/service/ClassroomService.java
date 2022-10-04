package com.ace.ai.student.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Attendance;
import com.ace.ai.student.datamodel.Classroom;
import com.ace.ai.student.dtomodel.ClassroomDTO;
import com.ace.ai.student.repository.AttendanceRepository;
import com.ace.ai.student.repository.ClassroomRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClassroomService {

    @Autowired
    ClassroomRepository classRoomRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    public String twelveHourFormat(String time) throws ParseException {

        final SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
        final Date dateObj = sdf.parse(time);

        log.info(new SimpleDateFormat("hh:mm a").format(dateObj));
        return new SimpleDateFormat("hh:mm a").format(dateObj);
    }
    
    public List<ClassroomDTO> showClassroomTable(Integer batchId) throws ParseException {

        List<ClassroomDTO> classroomDTOList = new ArrayList<>();
        List<Classroom> classroomList = classRoomRepository.findAllByBatchIdAndDeleteStatus(batchId, false);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (Classroom classroom : classroomList) {
            ClassroomDTO classroomDTO = new ClassroomDTO();
            classroomDTO.setId(classroom.getId());
            classroomDTO.setDate(LocalDate.parse(classroom.getDate(), df));
            classroomDTO.setLink(classroom.getLink());
            classroomDTO.setStatus("");
            classroomDTO.setTeacherName(classroom.getTeacherName());

            classroomDTO.setStartTime(twelveHourFormat(classroom.getStartTime()));
            classroomDTO.setEndTime(twelveHourFormat(classroom.getEndTime()));

            classroomDTO.setStartDateTime(
                    LocalDateTime.parse(classroomDTO.getDate() + " " + classroom.getStartTime(), dtf));

            classroomDTO
                    .setEndDateTime(LocalDateTime.parse(classroomDTO.getDate() + " " + classroom.getEndTime(), dtf));

            if (classroomDTO.getStartDateTime().isAfter(LocalDateTime.now())) {
                classroomDTO.setStatus("Upcoming");
            }

            else if (LocalDateTime.now().isAfter(classroomDTO.getEndDateTime())) {
                classroomDTO.setStatus("Done");
            }

            else if (LocalDateTime.now().isAfter(classroomDTO.getStartDateTime())
                    && LocalDateTime.now().isBefore(classroomDTO.getEndDateTime())) {
                classroomDTO.setStatus("In Progress");
            }

            log.info(classroomDTO.getStatus());

            classroomDTOList.add(classroomDTO);
        }

        return classroomDTOList;
    }

    public List<ClassroomDTO> comingClassroom(int batchId) throws ParseException{
        List<ClassroomDTO> classroomDTOs = showClassroomTable(batchId);

        List<ClassroomDTO> comingClassroom = new ArrayList<>();

        for(ClassroomDTO classroomDTO : classroomDTOs){
            if(classroomDTO.getStatus() == "Upcoming" || classroomDTO.getStatus() == "In Progress" ){
                comingClassroom.add(classroomDTO);
            }
        }
        return comingClassroom;
    }

    public List<ClassroomDTO> previousClassroom(int batchId, int studentId) throws ParseException{
        List<ClassroomDTO> classroomDTOs = showClassroomTable(batchId);

        List<ClassroomDTO> previousClassroom = new ArrayList<>();

        for(ClassroomDTO classroomDTO : classroomDTOs){
             if(classroomDTO.getStatus() == "Done"){
                Attendance attendance = attendanceRepository.findByStudent_IdAndClassroom_Id(studentId, classroomDTO.getId());
                if(attendance != null){
                    classroomDTO.setAttendanceStatus(attendance.getAttend());
                }else{
                    classroomDTO.setAttendanceStatus("Absent");
                }
                previousClassroom.add(classroomDTO);
            }
        }
        return previousClassroom;
    }
}
