package com.ace.ai.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.RecordVideo;
import com.ace.ai.student.repository.RecordVideoRepository;

@Service
public class RecordVideoService {
    @Autowired
    RecordVideoRepository recordVideoRepository;

    public List<RecordVideo> getRecordVideoByClassroomId(int classroomId){
        
        return recordVideoRepository.findByDeleteStatusAndClassroomId(0, classroomId);
    }

    public RecordVideo getRecordVideoById(int id){
        return recordVideoRepository.findById(id);
    }
}
