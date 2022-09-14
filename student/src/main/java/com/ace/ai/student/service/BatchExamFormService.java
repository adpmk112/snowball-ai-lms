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

import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.BatchExamForm;
import com.ace.ai.student.datamodel.ExamForm;
import com.ace.ai.student.dtomodel.StudentExamDoneDTO;
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

    public List<ExamForm> getUpcomingExamList(int batchId){
        List<BatchExamForm> allBef = batchExamFormRepository.findByDeleteStatusAndBatch_IdAndExamForm_DeleteStatus(false, batchId, false);
        List<ExamForm> upcomingExams = new ArrayList<>();
         //Now Time
         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         LocalDateTime now = LocalDateTime.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         String formattedString = now.format(formatter);
         LocalDateTime formattedNow = LocalDateTime.parse(formattedString, dtf);
         for(BatchExamForm bef: allBef){
            try{
                LocalDateTime startDate = LocalDateTime.parse(bef.getStartDate().replace("T", " "), dtf);
                if(formattedNow.isBefore(startDate)){
                    upcomingExams.add(bef.getExamForm());
                }
            }catch(Exception e){
                System.out.println("some errors occur in getFinishedExam "+ e);
            }
        }
        return upcomingExams;
    }
    
    public List<StudentExamDoneDTO> getFinishedExamList(int batchId, int studentId){
        List<BatchExamForm> allBef = batchExamFormRepository.findByDeleteStatusAndBatch_IdAndExamForm_DeleteStatus(false, batchId, false);
        List<StudentExamDoneDTO> finishedDoneDTO = new ArrayList<>();
        //Now Time
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = now.format(formatter);
        LocalDateTime formattedNow = LocalDateTime.parse(formattedString, dtf);
        for(BatchExamForm bef: allBef){
            if(!bef.getEndDate().isBlank() && bef.getEndDate() != null){
                LocalDateTime endDate = LocalDateTime.parse(bef.getEndDate().replace("T", " "), dtf);
                if(formattedNow.isAfter(endDate)){
                    int mark = (studentExamMarkRepository.findByExamForm_IdAndStudent_Id(bef.getExamForm().getId(), studentId) == null)? 0: studentExamMarkRepository.findByExamForm_IdAndStudent_Id(bef.getExamForm().getId(), studentId).getStudentMark();
                    StudentExamDoneDTO stuDExamDTO = new StudentExamDoneDTO(bef.getExamForm().getName(), 
                                                            bef.getExamForm().getType(),
                                                            mark,
                                                            bef.getExamForm().getMaxMark());
                    finishedDoneDTO.add(stuDExamDTO);
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
