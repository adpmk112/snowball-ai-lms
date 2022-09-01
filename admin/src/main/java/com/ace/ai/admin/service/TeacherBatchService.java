package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.engine.jdbc.batch.internal.BatchingBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.repository.TeacherBatchRepository;

@Service
public class TeacherBatchService {
    @Autowired
    TeacherBatchRepository teacherBatchRepository;

    public void deleteByBatchId(int batchId){
        System.out.println("batch id  is + "+ batchId);        
        List<TeacherBatch> tbs = teacherBatchRepository.findByBatchId(batchId);
        for(TeacherBatch tb : tbs){
            teacherBatchRepository.delete(tb);
        }
    }
}
