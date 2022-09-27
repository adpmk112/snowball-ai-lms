package com.ace.ai.admin.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterBatch;
import com.ace.ai.admin.datamodel.ChapterFile;
import com.ace.ai.admin.datamodel.CustomChapter;
import com.ace.ai.admin.datamodel.CustomChapterFile;
import com.ace.ai.admin.dtomodel.CustomAssignmentDTO;
import com.ace.ai.admin.repository.AssignmentRepository;
import com.ace.ai.admin.repository.ChapterBatchRepository;
import com.ace.ai.admin.repository.ChapterFileRepository;
import com.ace.ai.admin.repository.ChapterRepository;
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

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    ChapterBatchRepository chapterBatchRepository;

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

    public void customChapterAssignmentPlus(CustomChapter customChapter,Integer batchId,String fileName){
        
        Assignment assignment = new Assignment();

        Batch batch = new Batch();
        batch.setId(batchId);

        assignment.setAssignmentChapterName(customChapter.getName());
        assignment.setBatch(batch);
        assignment.setBranch("customChapter");
        assignment.setName(fileName);
        assignment.setEnd_date(customChapter.getEndDate());
        assignment.setEnd_time("23:59");
            
        assignmentRepository.save(assignment);
        log.info("assignment added into table");
    }


     public Assignment getUniqueAssignment(CustomChapter customChapter,Integer batchId,String fileName){

        List<Assignment>assignmentList = 
        findByAssignmentChapterNameAndBranchAndBatchId(customChapter.getName(), "customChapter", batchId);
        log.info(fileName);
        Assignment foundAssignment = new Assignment();

        for(Assignment assignment : assignmentList){
            log.info("Assignment.getName() --> "+assignment.getName());
            if(assignment.getName().equalsIgnoreCase(fileName)){
                foundAssignment.setId(assignment.getId());
                log.info(""+assignment.getId());
            }
        }

        foundAssignment = assignmentRepository.findById(foundAssignment.getId()).get();

        return foundAssignment;

 }

     public void customChapterAssignmentEdit(Assignment assignment, String newFileName){
        
         assignment.setName(newFileName);
            
         assignmentRepository.save(assignment);
         log.info("assignment updated into table");

     } 

     public void customChapterAssignmentDelete(Assignment assignment){
        
        assignment.setDeleteStatus(true);
           
        assignmentRepository.save(assignment);
        log.info("assignment deleted");

    } 


    public void customChapterAssignmentDelete(String assignmentChapterName, String branch, int batchId){
        List<Assignment> assignmentList = 
        findByAssignmentChapterNameAndBranchAndBatchId(assignmentChapterName, branch, batchId);

        for(Assignment assignment : assignmentList){
            assignment.setDeleteStatus(true);
            assignmentRepository.save(assignment);
        }

        log.info("assignment deleted");
    }

    public void assignmentEndDateAdd(String endDate, String chapterName, int batchId){
        List<Assignment> assignmentList = assignmentRepository.findByDeleteStatusAndBatchIdAndAssignmentChapterName(false, batchId, chapterName);
        for(Assignment assignment : assignmentList){
            assignment.setEnd_date(endDate);
            log.info(endDate);
            assignment.setEnd_time("23:59");
            assignmentRepository.save(assignment);
        }
        log.info("Ok assignment EndDate");
    }

    public void customAssignmentAdd(CustomAssignmentDTO customAssignmentDTO,int batchId, int chapterId){
        
        try{

        for (MultipartFile assignment : customAssignmentDTO.getAssignment()) {
            if (!assignment.isEmpty()) {

            Chapter chapter = chapterRepository.findById(chapterId).get();

            ChapterBatch chapterBatch = chapterBatchRepository.findChapterBatchByBatchIdAndChapterId(batchId, chapterId);

            Batch batch = new Batch();
            batch.setId(batchId);
            
            Assignment assignment1 = new Assignment();

            assignment1.setAssignmentChapterName(chapter.getName());
            assignment1.setBatch(batch);
            assignment1.setBranch("customAssignment");
            assignment1.setName(assignment.getOriginalFilename());
            assignment1.setEnd_date(chapterBatch.getEndDate());
            assignment1.setEnd_time("23:59");
                
            assignmentRepository.save(assignment1);

            }
        }
            String uploadDir = "./assets/img/customAssignment/" +chapterId;
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile assignment : customAssignmentDTO.getAssignment()) {
                if (!assignment.isEmpty()) {
                    try (InputStream inputStream = assignment.getInputStream()) {
                        Path filePath = uploadPath.resolve(assignment.getOriginalFilename());
                        System.out.println(filePath.toFile().getAbsolutePath());
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new IOException("Could not save upload assignment: " + assignment.getOriginalFilename());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        }

    public List<Assignment> findByAssignmentChapterNameAndBranchAndBatchId(String assignmentChapterName, String branch, int batchId){
        return assignmentRepository.findByAssignmentChapterNameAndBranchAndBatchId(assignmentChapterName, branch, batchId);
    }

    public Assignment getById(int id){
        return assignmentRepository.getById(id);
    }

}
