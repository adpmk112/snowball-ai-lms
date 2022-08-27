package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Chapter;

@Repository
public interface ChapterRepository  extends JpaRepository<Chapter, Integer> {
    Chapter findByName(String chpName);
    List<Chapter> findByCourseIdAndDeleteStatus(int courseId,int deleteStatus);
}
