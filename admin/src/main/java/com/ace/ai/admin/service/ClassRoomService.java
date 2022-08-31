package com.ace.ai.admin.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.dtomodel.ClassroomDTO;
import com.ace.ai.admin.dtomodel.ReqClassroomDTO;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.ClassRoomRepository;
import com.ace.ai.admin.repository.TeacherBatchRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassRoomService {
    
    @Autowired
    ClassRoomRepository classRoomRepository;

    @Autowired
    TeacherBatchRepository teacherBatchRepository;

    @Autowired
    BatchRepository batchRepository;

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

    public List<ClassroomDTO> fetchTeacherListForClassroom(Integer batchId){
        List<TeacherBatch> teacherNameList = teacherBatchRepository.findByBatchId(batchId);
        List<ClassroomDTO> classroomDTOforTeacherList = new ArrayList<>();

        for(TeacherBatch teacherBatch : teacherNameList){
            ClassroomDTO classroomDTOforTeacher = new ClassroomDTO();

            classroomDTOforTeacher.setTeacherName(teacherBatch.getTeacher().getName());
            classroomDTOforTeacher.setBatchId(batchId);
            classroomDTOforTeacherList.add(classroomDTOforTeacher);
        }

        return classroomDTOforTeacherList;
    }

    public static String englishTime(String input)
    throws ParseException
{

    // Format of the date defined in the input String
    DateFormat dateFormat
        = new SimpleDateFormat("hh:mm");
   
    // Change the pattern into 24 hour format
    DateFormat format
        = new SimpleDateFormat("HH:mm");
    Date time = null;
    String output = "";
   
    // Converting the input String to Date
    time = dateFormat.parse(input);
   
    // Changing the format of date
    // and storing it in
    // String
    output = format.format(time);
    return output;
}

    public void createClassroom(ReqClassroomDTO reqClassroomDTO) throws ParseException{
        Classroom classroom = new Classroom();

        // LocalDate classroomDate = classroomDTO.getDate();
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // classroom.setDate(classroomDate.format(formatter));
        // log.info(classroom.getDate()+"format to string is ok.");

        classroom.setDate(reqClassroomDTO.getDate());
        classroom.setLink(reqClassroomDTO.getLink());
        classroom.setBatch(batchRepository.findBatchById(reqClassroomDTO.getBatchId()));
        classroom.setTeacherName(reqClassroomDTO.getTeacherName());
        classroom.setStartTime(englishTime(reqClassroomDTO.getStartTime()));
        classroom.setEndTime(englishTime(reqClassroomDTO.getEndTime()));
        classRoomRepository.save(classroom);
    }
}
