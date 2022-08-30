package com.ace.ai.admin.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.dtomodel.ClassroomDTO;
import com.ace.ai.admin.repository.ClassRoomRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassRoomService {
    
    @Autowired
    ClassRoomRepository classRoomRepository;

    public List<ClassroomDTO> showClassroomTable(Integer batchId){

        List<ClassroomDTO> classroomDTOList = new ArrayList<>();
        List<Classroom> classroomList = classRoomRepository.findAllByBatchIdAndDeleteStatus(batchId,false);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dtf
        = DateTimeFormatter.ofPattern(
            "yyyy-MM-dd HH:mm");

        for(Classroom classroom: classroomList){
            ClassroomDTO classroomDTO = new ClassroomDTO();
            log.info(classroom.getDate());
            classroomDTO.setDate(LocalDate.parse(classroom.getDate(),df));
            classroomDTO.setLink(classroom.getLink());
            classroomDTO.setRecordedVideo(classroom.getRecordVideo());
            classroomDTO.setStatus("");
            classroomDTO.setTeacherName(classroom.getTeacherName());

            classroomDTO.setStartTime(LocalTime.parse(classroom.getStartTime(),tf));
            classroomDTO.setEndTime(LocalTime.parse(classroom.getEndTime())); 

            classroomDTO.setStartDateTime(LocalDateTime.parse
            (classroomDTO.getDate()+" "+classroomDTO.getStartTime(),dtf));

            classroomDTO.setEndDateTime(LocalDateTime.parse
            (classroomDTO.getDate()+" "+classroomDTO.getEndTime(),dtf));

            if(classroomDTO.getStartDateTime().isAfter(LocalDateTime.now())){
                classroomDTO.setStatus("Upcoming");
            }

            else if(LocalDateTime.now().isAfter
            (classroomDTO.getEndDateTime())){
                classroomDTO.setStatus("Done");
            }

            else if(LocalDateTime.now().isAfter(classroomDTO.getStartDateTime())
            && LocalDateTime.now().isBefore(classroomDTO.getEndDateTime()) ){
                classroomDTO.setStatus("In Progress");
            }

            log.info(classroomDTO.getStatus());
            
            classroomDTOList.add(classroomDTO);
        }
         
        return classroomDTOList;
    } 
}
