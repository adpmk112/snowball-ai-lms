package com.ace.ai.admin.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.dtomodel.ExamScheduleDTO;
import com.ace.ai.admin.repository.BatchExamFormRepository;
import com.ace.ai.admin.repository.ExamFormRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExamScheduleService {
    
    @Autowired
    BatchExamFormRepository batchExamFormRepository;

    @Autowired
    ExamFormRepository examFormRepository;

    public List<ExamScheduleDTO> showExamScheduleTable(Integer batchId){

        List<ExamScheduleDTO>examScheduleDTOList = new ArrayList<>();
        List<BatchExamForm>batchExamFormList = batchExamFormRepository.findAllByBatchId(batchId);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        
        for(BatchExamForm batchExamForm:batchExamFormList){

            ExamScheduleDTO examScheduleDTO = new ExamScheduleDTO();
            examScheduleDTO.setExamName(batchExamForm.getExamForm().getName());
            log.info(examScheduleDTO.getExamName());
            examScheduleDTO.setStartDate(LocalDate.parse(batchExamForm.getStartDate(), df));
            examScheduleDTO.setEndDate(LocalDate.parse(batchExamForm.getEndDate(),df));
            
            if(examScheduleDTO.getStartDate().isAfter(LocalDate.now())){
                examScheduleDTO.setStatus("Upcoming");
            }

            else if (examScheduleDTO.getStartDate().atStartOfDay().isBefore(LocalDateTime.now())
            && LocalDate.now().isBefore(examScheduleDTO.getEndDate())){
                examScheduleDTO.setStatus("In Progress");
            }

            else if (LocalDate.now().isAfter(examScheduleDTO.getEndDate())){
                examScheduleDTO.setStatus("Done");
            }

            examScheduleDTOList.add(examScheduleDTO);
        }

        return examScheduleDTOList;
    }
}
