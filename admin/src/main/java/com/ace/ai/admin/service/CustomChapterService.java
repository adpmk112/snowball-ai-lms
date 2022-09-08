package com.ace.ai.admin.service;

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
}
