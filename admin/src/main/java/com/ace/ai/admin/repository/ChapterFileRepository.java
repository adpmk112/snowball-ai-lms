package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ai.admin.datamodel.ChapterFile;

public interface ChapterFileRepository extends JpaRepository<ChapterFile,Integer> {
    List<ChapterFile> findByChapterIdAndDeleteStatus(int chapterId,int deleteStatus);
    ChapterFile findById(int chapterFileId);
    List<ChapterFile> findByChapterIdAndFileTypeAndDeleteStatus(int chapterId,String fileType,int deleteStatus);
   
    
}
