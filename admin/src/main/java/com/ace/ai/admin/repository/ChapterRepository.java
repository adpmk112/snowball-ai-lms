package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChapterRepository  extends JpaRepository<Chapter, Integer> {
    Chapter findByName(String chpName);
}
