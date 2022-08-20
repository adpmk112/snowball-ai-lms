package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.datamodel.ExamForm;
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

        for(BatchExamForm batchExamForm:batchExamFormList){

            ExamScheduleDTO examScheduleDTO = new ExamScheduleDTO();
            examScheduleDTO.setExamName(batchExamForm.getExamForm().getName());
            log.info(examScheduleDTO.getExamName());
            examScheduleDTO.setStartDate(batchExamForm.getStartDate());
            examScheduleDTO.setEndDate(batchExamForm.getEndDate());
            examScheduleDTO.setStatus("");

            examScheduleDTOList.add(examScheduleDTO);
        }

        return examScheduleDTOList;
    }
}
