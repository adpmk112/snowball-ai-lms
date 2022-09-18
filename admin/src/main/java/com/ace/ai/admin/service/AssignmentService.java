package com.ace.ai.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterFile;
import com.ace.ai.admin.repository.AssignmentRepository;
import com.ace.ai.admin.repository.ChapterFileRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AssignmentService {
    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    ChapterFileRepository chapterFileRepository;

    public void saveAssignment(Assignment assignment){
        assignmentRepository.save(assignment);
    }

    public void baseChapterAssignmentFileAdd(Chapter chapter,Integer batchId){

        List<ChapterFile> chapterFileList = chapterFileRepository.findByChapterIdAndFileTypeAndDeleteStatus(chapter.getId(), "assignment", 0);
        
        for(ChapterFile chapterFile:chapterFileList){
            Assignment assignment = new Assignment();
            
            Batch batch = new Batch();
            batch.setId(batchId);

            assignment.setAssignmentChapterName(chapter.getName());
            assignment.setBranch("baseChapter");
            assignment.setName(chapterFile.getName());
            assignment.setBatch(batch);
            assignmentRepository.save(assignment);
            log.info("assignment added into table");
        }
    }

    public Assignment getById(int id){
        return assignmentRepository.getById(id);
    }
}
