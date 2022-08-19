package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchService {
    @Autowired
    BatchRepository batchRepository;

    public List<Batch> findAllBatch(){
        return batchRepository.findAll();
    }

}
