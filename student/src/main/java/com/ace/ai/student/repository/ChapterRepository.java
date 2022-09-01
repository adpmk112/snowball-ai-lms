package com.ace.ai.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter,Integer>{
    
}
