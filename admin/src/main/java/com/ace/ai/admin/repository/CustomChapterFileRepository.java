package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ace.ai.admin.datamodel.CustomChapterFile;

@Repository
public interface CustomChapterFileRepository extends JpaRepository<CustomChapterFile,Integer>{
    List<CustomChapterFile> findByCustomChapterIdAndDeleteStatus(int customChapterId,boolean deleteStatus);
    List<CustomChapterFile> findByCustomChapterIdAndFileTypeAndDeleteStatus(int customChapterId,String fileType,boolean deleteStatus);
}
