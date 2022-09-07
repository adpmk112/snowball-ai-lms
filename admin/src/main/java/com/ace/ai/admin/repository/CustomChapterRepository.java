package com.ace.ai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.CustomChapter;

@Repository
public interface CustomChapterRepository extends JpaRepository<CustomChapter,Integer> {
    CustomChapter findByName(String name);
}
