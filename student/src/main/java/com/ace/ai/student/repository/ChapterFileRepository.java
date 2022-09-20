package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.ChapterFile;

@Repository
public interface ChapterFileRepository extends JpaRepository<ChapterFile,Integer>{
    List<ChapterFile> findByChapterIdAndDeleteStatus(int chapterId,int deleteStatus);
    
}
