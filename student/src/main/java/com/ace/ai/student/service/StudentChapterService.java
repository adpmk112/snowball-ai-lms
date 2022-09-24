package com.ace.ai.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Assignment;
import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.Chapter;
import com.ace.ai.student.datamodel.ChapterFile;
import com.ace.ai.student.datamodel.CustomChapter;
import com.ace.ai.student.datamodel.CustomChapterFile;
import com.ace.ai.student.dtomodel.ChapterFileDTO;
import com.ace.ai.student.repository.AssignmentRepository;
import com.ace.ai.student.repository.BatchRepository;
import com.ace.ai.student.repository.ChapterFileRepository;
import com.ace.ai.student.repository.ChapterRepository;
import com.ace.ai.student.repository.CustomChapterFileRepository;
import com.ace.ai.student.repository.CustomChapterRepository;

@Service
public class StudentChapterService {
    @Autowired
    ChapterRepository chapterRepository;
    @Autowired
    CustomChapterRepository customChapterRepository;
    @Autowired
    ChapterFileRepository chapterFileRepository;
    @Autowired
    CustomChapterFileRepository customChapterFileRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    AssignmentRepository assignmentRepository;

    public List<ChapterFileDTO> getChapterFileListByChapterId(int chapterId) {
        List<ChapterFile> chapterFileList = chapterFileRepository.findByChapterIdAndDeleteStatus(chapterId, 0);

        List<ChapterFileDTO> chapterFileDTOList = new ArrayList<>();
        for (ChapterFile chapterFile : chapterFileList) {
            ChapterFileDTO chapterFileDTO = new ChapterFileDTO();
            chapterFileDTO.setChapterId(chapterId);
            chapterFileDTO.setId(chapterFile.getId());
            chapterFileDTO.setName(chapterFile.getName());
            chapterFileDTO.setFileType(chapterFile.getFileType());
            
            chapterFileDTOList.add(chapterFileDTO);
        }
        return chapterFileDTOList;
    }

    // custom chapter and chapter are the same chapterFileDTO
    public List<ChapterFileDTO> getCustomChapterFileListByCustomChapterId(int customChapterId) {
        List<CustomChapterFile> customChapterFileList = customChapterFileRepository
                .findByCustomChapterIdAndDeleteStatus(customChapterId, false);

        List<ChapterFileDTO> chapterFileDTOList = new ArrayList<>();
        for (CustomChapterFile customChapterFile : customChapterFileList) {
            ChapterFileDTO chapterFileDTO = new ChapterFileDTO();
            chapterFileDTO.setChapterId(customChapterId);
            chapterFileDTO.setId(customChapterFile.getId());
            chapterFileDTO.setName(customChapterFile.getName());
            chapterFileDTO.setFileType(customChapterFile.getFileType());
            
            chapterFileDTOList.add(chapterFileDTO);
        }
        return chapterFileDTOList;
    }

    public List<Assignment> getAssignmentListByChapterNameAndBatchId(String chapterName,int batchId){
        List<Assignment> assignmentList = assignmentRepository.findByAssignmentChapterNameAndDeleteStatusAndBatchId(chapterName, false, batchId);
        return assignmentList;
    }
    public Chapter getChapterById(int chapterId){
        return chapterRepository.findById(chapterId).get();
    }
    public CustomChapter getCustomChapterById(int customChapterId){
        return customChapterRepository.findById(customChapterId).get();
    }

    public ChapterFileDTO getChapterFileById(int chapterFileId, int chapterId) {
        ChapterFile chapterFile = chapterFileRepository.findById(chapterFileId).get();
        ChapterFileDTO chapterFileDTO = new ChapterFileDTO();
        chapterFileDTO.setChapterId(chapterId);
        chapterFileDTO.setId(chapterFile.getId());
        chapterFileDTO.setName(chapterFile.getName());
        chapterFileDTO.setFileType(chapterFile.getFileType());

        return chapterFileDTO;
    }

    public Chapter findChapterById(int chapterId){
        return chapterRepository.findById(chapterId).get();
    }

    public Batch findBatchById(int batchId){
        return batchRepository.findById(batchId).get();
    }
    public CustomChapter findCustomChapterById(int customChapterId){
        return customChapterRepository.findById(customChapterId).get();
    }

    public ChapterFileDTO getCustomChapterFileById(int customChapterFileId, int customChapterId) {
        CustomChapterFile customChapterFile = customChapterFileRepository.findById(customChapterFileId).get();
        ChapterFileDTO chapterFileDTO = new ChapterFileDTO();
        chapterFileDTO.setChapterId(customChapterId);
        chapterFileDTO.setId(customChapterFile.getId());
        chapterFileDTO.setName(customChapterFile.getName());
        chapterFileDTO.setFileType(customChapterFile.getFileType());

        return chapterFileDTO;
    }

}
