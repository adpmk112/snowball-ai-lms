package com.ace.ai.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.CustomChapter;
import com.ace.ai.admin.datamodel.CustomChapterFile;
import com.ace.ai.admin.repository.CustomChapterFileRepository;
import com.ace.ai.admin.repository.CustomChapterRepository;

@Service
public class CustomChapterService {
    @Autowired
    CustomChapterRepository customChapterRepository;
    @Autowired
    CustomChapterFileRepository customChapterFileRepository;

    public void createNewActivity(CustomChapter customChapter){
        customChapterRepository.save(customChapter);
    }

    public int getChapterIdByName(String chapterName){
        CustomChapter customChapter = customChapterRepository.findByName(chapterName);
        return customChapter.getId();
    }

    public void saveCustomChapterFile(CustomChapterFile customChapterFile){
        customChapterFileRepository.save(customChapterFile);
    }

    public  List<CustomChapterFile> getCustomChapterFileListById(int customChapterId){
        return customChapterFileRepository.findByCustomChapterIdAndDeleteStatus(customChapterId, false);
    }

    public CustomChapter getCustomChapterById(int customChapterId){
        return customChapterRepository.findById(customChapterId).get();
    }

    public CustomChapterFile getCustomChapterFileById(int customChapterId){
        return customChapterFileRepository.findById(customChapterId).get();
    }

    public void deleteCustomChapterFile(int customChapterFileId) {
        CustomChapterFile customChapterFile = customChapterFileRepository.findById(customChapterFileId).get();
        customChapterFile.setDeleteStatus(true);
        customChapterFileRepository.save(customChapterFile);
    }

    public void editCustomChapter(int customChapterId, String customChapterName) {
        CustomChapter customChapter = customChapterRepository.findById(customChapterId).get();
        customChapter.setName(customChapterName);
        customChapterRepository.save(customChapter);
    }
   
}
