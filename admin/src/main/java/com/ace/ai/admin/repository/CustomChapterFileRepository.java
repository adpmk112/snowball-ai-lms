package com.ace.ai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.CustomChapterFile;

@Repository
public interface CustomChapterFileRepository extends JpaRepository<CustomChapterFile,Integer>{
    
}
