package com.ace.ai.student.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.text.DateFormat;
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
        Assignment assignment = assignmentRepository.findByIdAndDeleteStatus(assignmentId, false);
        AssignmentDateTimeDTO assignmentDateTimeDTO = new AssignmentDateTimeDTO();
        assignmentDateTimeDTO.setFileName(assignment.getName());
        if(assignment != null){
        assignmentDateTimeDTO.setEnd_date(assignment.getEnd_date());
        assignmentDateTimeDTO.setEnd_time(twelveHourFormat(assignment.getEnd_time()));
        }
        return assignmentDateTimeDTO;
    }

    public AssignmentMarkDTO getStudentMarkByAssiIdAndStuId(int assignmentId, int studentId) throws ParseException{
        int studentMark;
        String submitDate;
        String submitTime;
        AssignmentMarkDTO assignmentMarkDTO = new AssignmentMarkDTO();
        StudentAssignmentMark studentAssignmentMark =studentAssignmentMarkRepository.findByAssignment_IdAndStudent_Id(assignmentId , studentId);
        if(studentAssignmentMark != null){
            if(studentAssignmentMark.getStudentMark() == 0 && !studentAssignmentMark.getDate().isBlank() && !studentAssignmentMark.getTime().isBlank()){
                studentMark = 100;
                submitDate = studentAssignmentMark.getDate();
                submitTime = twelveHourFormat(studentAssignmentMark.getTime());
            }
            else{
                studentMark = studentAssignmentMark.getStudentMark();
                submitDate = studentAssignmentMark.getDate();
                submitTime = twelveHourFormat(studentAssignmentMark.getTime());
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

    public  String englishTime(String input)
            throws ParseException {

        // Format of the date defined in the input String
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");

        // Change the pattern into 24 hour format
        DateFormat format = new SimpleDateFormat("HH:mm");
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
    
    public String getStatusAssignment(int assignmentId) throws ParseException{
        Assignment assignment = assignmentRepository.findByIdAndDeleteStatus(assignmentId, false);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.now();
        String currentTime = localTime.format(timeFormatter);
        LocalTime localCurrentTime =LocalTime.parse(currentTime,timeFormatter);
        String status =null;
        LocalDate dueDate;
        LocalTime dueTime;
        if(assignment != null && assignment.getEnd_date() != null && assignment.getEnd_time() != null){
             dueDate = LocalDate.parse(assignment.getEnd_date(), dateFormatter);
             dueTime = LocalTime.parse(assignment.getEnd_time(), timeFormatter);            
            if(dueDate.isBefore(LocalDate.now()) == false){                
                    if(dueTime.compareTo(localCurrentTime) == 0 ){
                        status = "early2";
                    }
                    
                    else if(dueTime.isBefore(localCurrentTime) == true ){
                        status = "late2";
                    }
                    else if(dueTime.isBefore(localCurrentTime) == false)
                    status = "early1";
                    
            }    
            else if(dueDate.isBefore(LocalDate.now()) == true){
                status = "late1";
            } 
        }   
        return status;
    }

    public String getStatusAssignmentId(int assignmentId,int studentId){
        Assignment assignment = assignmentRepository.findByIdAndDeleteStatus(assignmentId, false);
        StudentAssignmentMark studentAssignmentMark = studentAssignmentMarkRepository.findByAssignment_IdAndStudent_Id(assignmentId,studentId);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.now();
        String currentTime = localTime.format(timeFormatter);
        LocalTime localCurrentTime =LocalTime.parse(currentTime,timeFormatter);
        String status =null;
        LocalDate dueDate,submitDate;
        LocalTime dueTime,submitTime;
        if(assignment != null && studentAssignmentMark == null){
            if(assignment.getEnd_date() != null && assignment.getEnd_time() != null){
                dueDate = LocalDate.parse(assignment.getEnd_date(), dateFormatter);
                dueTime = LocalTime.parse(assignment.getEnd_time(), timeFormatter);            
               if(dueDate.isBefore(LocalDate.now()) == false){                
                       if(dueTime.compareTo(localCurrentTime) == 0 ){
                           status = "early2";
                       }
                       
                       else if(dueTime.isBefore(localCurrentTime) == true ){
                           status = "late2";
                       }
                       else if(dueTime.isBefore(localCurrentTime) == false)
                       status = "early1";
                       
               }    
               else if(dueDate.isBefore(LocalDate.now()) == true){
                   status = "late1";
               } 
           }   
        }
        else if(assignment != null && studentAssignmentMark != null){
            
                if(assignment.getEnd_date() != null && assignment.getEnd_time() != null && studentAssignmentMark.getDate() != null && studentAssignmentMark.getTime() != null){
                    dueDate = LocalDate.parse(assignment.getEnd_date(), dateFormatter);
                    dueTime = LocalTime.parse(assignment.getEnd_time(), timeFormatter); 
                    submitDate = LocalDate.parse(studentAssignmentMark.getDate(), dateFormatter);
                    submitTime = LocalTime.parse(studentAssignmentMark.getTime(), timeFormatter);            
                   if(dueDate.isBefore(submitDate) == false){                
                           if(dueTime.compareTo(submitTime) == 0 ){
                               status = "early2";
                           }
                           
                           else if(dueTime.isBefore(submitTime) == true ){
                               status = "late2";
                           }
                           else if(dueTime.isBefore(submitTime) == false)
                           status = "early1";
                           
                   }    
                   else if(dueDate.isBefore(submitDate) == true){
                       status = "late1";
                   } 
               }   
            
        }
        return status;
    }

    }

