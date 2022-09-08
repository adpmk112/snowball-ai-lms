package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomChapterFile extends JpaRepository<CustomChapterFile,Integer> {
    List<CustomChapterFile> findByCustomChapterId(int customChapterId); 
}
