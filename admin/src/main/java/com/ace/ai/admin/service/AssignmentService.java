package com.ace.ai.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterFile;
import com.ace.ai.admin.datamodel.CustomChapter;
import com.ace.ai.admin.datamodel.CustomChapterFile;
import com.ace.ai.admin.repository.AssignmentRepository;
import com.ace.ai.admin.repository.ChapterFileRepository;
import com.ace.ai.admin.repository.CustomChapterFileRepository;
import com.ace.ai.admin.repository.CustomChapterRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AssignmentService {
    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    ChapterFileRepository chapterFileRepository;

    @Autowired
    CustomChapterFileRepository customChapterFileRepository;

    @Autowired
    CustomChapterRepository customChapterRepository;

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
    public void customChapterAssignmentFileAdd(CustomChapter customChapter,Integer batchId){

        List<CustomChapterFile> customChapterFileList = customChapterFileRepository.findByCustomChapterIdAndFileTypeAndDeleteStatus(customChapter.getId(), "assignment", false);

        for(CustomChapterFile customChapterFile:customChapterFileList){
            Assignment assignment = new Assignment();
            
            Batch batch = new Batch();
            batch.setId(batchId);

            CustomChapter customChapter1 = customChapterRepository.findById(customChapterFile.getCustomChapter().getId()).get();

            assignment.setAssignmentChapterName(customChapter1.getName());
            assignment.setBranch("customChapter");
            assignment.setName(customChapterFile.getName());
            assignment.setBatch(batch);
            assignmentRepository.save(assignment);
            log.info("assignment added into table");
        }
    }

    public Assignment getById(int id){
        return assignmentRepository.getById(id);
    }
}
