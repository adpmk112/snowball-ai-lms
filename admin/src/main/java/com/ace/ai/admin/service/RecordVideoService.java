package com.ace.ai.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.RecordVideo;
import com.ace.ai.admin.repository.RecordVideoRepository;

@Service
public class RecordVideoService {
    @Autowired
    RecordVideoRepository recordVideoRepository;

    public List<RecordVideo> getRecordVideoByClassroomId(int classroomId){
        
        return recordVideoRepository.findByDeleteStatusAndClassroomId(0, classroomId);
    }

    public void saveReordVideo(RecordVideo recordVideo){
        recordVideoRepository.save(recordVideo);
    }

    public RecordVideo getRecordVideoById(int id){
        return recordVideoRepository.findById(id);
    }

    public void deleteRecordVideo(int recordVideoId){
        RecordVideo recordVideo = recordVideoRepository.findById(recordVideoId);
        recordVideo.setDeleteStatus(1);
        recordVideoRepository.save(recordVideo);
    }
}
