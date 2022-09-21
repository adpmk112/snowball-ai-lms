package com.ace.ai.student.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.plaf.synth.SynthToggleButtonUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.BatchExamForm;
import com.ace.ai.student.datamodel.ExamForm;
import com.ace.ai.student.datamodel.StudentExamMark;
import com.ace.ai.student.dtomodel.StudentExamDTO;
import com.ace.ai.student.repository.BatchExamFormRepository;
import com.ace.ai.student.repository.ExamFormRepository;
import com.ace.ai.student.repository.StudentExamMarkRepository;

import lombok.extern.slf4j.Slf4j;

@Service
public class BatchExamFormService {
    
    @Autowired
    BatchExamFormRepository batchExamFormRepository;

    @Autowired
    ExamFormRepository examFormRepository;

    @Autowired
    StudentExamMarkRepository studentExamMarkRepository;

    public List<StudentExamDTO> getUpcomingExamList(int batchId, int studentId){
        List<BatchExamForm> allBef = batchExamFormRepository.findByDeleteStatusAndBatch_IdAndExamForm_DeleteStatus(false, batchId, false);
        List<StudentExamDTO> upcomingStudentExamDTOList = new ArrayList<>();
         //Now Time
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         LocalDateTime now = LocalDateTime.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         String formattedString = now.format(formatter);
         LocalDateTime formattedNow = LocalDateTime.parse(formattedString, dtf);
         for(BatchExamForm bef: allBef){
            StudentExamMark studentExamMark = studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(bef.getId(), studentId);
            if(bef.getStartDate()!=null && !bef.getStartDate().isBlank() && bef.getEndDate()!=null && !bef.getEndDate().isBlank() && studentExamMark == null){// all that are scheduled and student is not answered               
                System.out.println("in progress");
                StudentExamDTO studentExamDTO = new StudentExamDTO();
                LocalDateTime startDate = LocalDateTime.parse(bef.getStartDate().replace("T", " "), dtf);
                LocalDateTime endDate = LocalDateTime.parse(bef.getEndDate().replace("T", " "), dtf);
                if(formattedNow.isAfter(startDate) && formattedNow.isBefore(endDate)){//Current open Exam
                    studentExamDTO.setStatus("Now starting...");
                    studentExamDTO.setStartDate(bef.getStartDate().replace("T", " "));
                    studentExamDTO.setEndDate(bef.getEndDate().replace("T", " "));
                    studentExamDTO.setExam(bef.getExamForm());
                    upcomingStudentExamDTOList.add(studentExamDTO);
                }
                else if(formattedNow.isBefore(startDate)){
                    studentExamDTO.setStartDate(bef.getStartDate());
                    studentExamDTO.setEndDate(bef.getEndDate());
                    studentExamDTO.setExam(bef.getExamForm());
                    studentExamDTO.setStatus("Will start at "+bef.getStartDate().replace("T"," "));
                    upcomingStudentExamDTOList.add(studentExamDTO);           
                } 
            }
        }
        return upcomingStudentExamDTOList;
    }
    
    public List<StudentExamDTO> getFinishedExamList(int batchId, int studentId){
        List<BatchExamForm> allBef = batchExamFormRepository.findByDeleteStatusAndBatch_IdAndExamForm_DeleteStatus(false, batchId, false);
        List<StudentExamDTO> finishedDoneDTO = new ArrayList<>();
        //Now Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = now.format(formatter);
        LocalDateTime formattedNow = LocalDateTime.parse(formattedString, dtf);
        
        for(BatchExamForm bef: allBef){
            if(!bef.getEndDate().isBlank() && bef.getEndDate() != null){
                LocalDateTime endDate = LocalDateTime.parse(bef.getEndDate().replace("T", " "), dtf);
                if(formattedNow.isAfter(endDate) || studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(bef.getId(), studentId) != null){
                    int mark = (studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(bef.getId(), studentId) == null)? 0: studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(bef.getId(), studentId).getStudentMark();
                    StudentExamDTO studentExamDTO = new StudentExamDTO();
                    studentExamDTO.setExam(bef.getExamForm());
                    studentExamDTO.setMark(mark);
                    studentExamDTO.setAnswerDate((studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(bef.getId(), studentId) == null)?"You haven't answered yet.": studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(bef.getId(), studentId).getAnswerDate());
                    finishedDoneDTO.add(studentExamDTO);
                }
                
            }
        }

        return finishedDoneDTO;
    }
  
    public void saveBathExamFrom(BatchExamForm bef){
        batchExamFormRepository.save(bef);
    }

    public BatchExamForm findById(int id){
        return batchExamFormRepository.getById(id);
    }

}
