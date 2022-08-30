package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.repository.TeacherBatchRepository;

@Service
public class TeacherBatchService {
    @Autowired
    TeacherBatchRepository teacherBatchRepository;

    public void deleteTeacherByBatchId(int batchId){
        teacherBatchRepository.deleteByBatchId(batchId);
    }
}
