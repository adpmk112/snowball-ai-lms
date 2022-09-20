package com.ace.ai.student.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.text.ParseException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ace.ai.student.datamodel.Assignment;
import com.ace.ai.student.datamodel.StudentAssignmentMark;
import com.ace.ai.student.dtomodel.AssignmentDateTimeDTO;
import com.ace.ai.student.dtomodel.AssignmentMarkDTO;
import com.ace.ai.student.repository.AssignmentRepository;
import com.ace.ai.student.repository.StudentAssignmentMarkRepository;


@Service
public class AssignmentService {

    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    StudentAssignmentMarkRepository studentAssignmentMarkRepository;
    
    public AssignmentDateTimeDTO getDateTimeByAssignmentId(int assignmentId) throws ParseException{
        Assignment assignment = assignmentRepository.findById(assignmentId);
        AssignmentDateTimeDTO assignmentDateTimeDTO = new AssignmentDateTimeDTO();
        if(assignment != null){
        assignmentDateTimeDTO.setEnd_date(assignment.getEnd_date());
        assignmentDateTimeDTO.setEnd_time(twelveHourFormat(assignment.getEnd_time()));
        }
        else{
            assignmentDateTimeDTO.setEnd_date("Date");
            assignmentDateTimeDTO.setEnd_time("Time");
        }
        return assignmentDateTimeDTO;
    }

    public AssignmentMarkDTO getStudentMarkByAssiIdAndStuId(int assignmentId, int studentId){
        int studentMark;
        String submitDate;
        String submitTime;
        AssignmentMarkDTO assignmentMarkDTO = new AssignmentMarkDTO();
        StudentAssignmentMark studentAssignmentMark =studentAssignmentMarkRepository.findByAssignment_IdAndStudent_Id(assignmentId , studentId);
        if(studentAssignmentMark != null){
            if(studentAssignmentMark.getStudentMark() == 0 && studentAssignmentMark.getDate().isBlank() && studentAssignmentMark.getTime().isBlank()){
                studentMark = 100;
                submitDate="SubmitDate";
                submitTime="SubmitTime";
            }
            else{
                studentMark = studentAssignmentMark.getStudentMark();
                submitDate = studentAssignmentMark.getDate();
                submitTime = studentAssignmentMark.getTime();
            }
        }
        else{
            studentMark =100;
            submitDate = "SubmitDate";
            submitTime = "SubmitTime";
        }
        assignmentMarkDTO.setStudentMark(studentMark);
        assignmentMarkDTO.setSubmitDate(submitDate);
        assignmentMarkDTO.setSubmitTime(submitTime);
        return assignmentMarkDTO;   
    }

    public String twelveHourFormat(String time) throws ParseException {
        final SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
        final Date dateObj = sdf.parse(time);
        return new SimpleDateFormat("hh:mm a").format(dateObj);
    }
          
    public String getStatusAssignment(int assignmentId){
        Assignment assignment = assignmentRepository.findById(assignmentId);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate localDueDate = LocalDate.parse(assignment.getEnd_date(), dateFormatter);
        LocalTime localDueTime = LocalTime.parse(assignment.getEnd_time(), timeFormatter);
        String status =null;
        if(assignment!=null){
            if(localDueDate.isAfter(LocalDate.now()) && localDueTime.isAfter(LocalTime.now())){
                status = "eailer";
            }
            else{
                status = "late";
            }
        }        
        return status;
    }

    }

