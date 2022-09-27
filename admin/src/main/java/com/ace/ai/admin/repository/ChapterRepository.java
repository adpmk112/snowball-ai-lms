package com.ace.ai.admin.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Chapter;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Integer> {
    Chapter findByName(String chpName);
    Chapter findByNameAndCourseId(String chpName,int courseId);
    List<Chapter> findByCourseIdAndDeleteStatus(int courseId,int deleteStatus);
    Optional<Chapter> findById(Integer chapterId);

    @Query(value = "SELECT * FROM chapter WHERE id=(SELECT max(id) FROM chapter);", nativeQuery = true)
    Chapter findLastChapter();
}
