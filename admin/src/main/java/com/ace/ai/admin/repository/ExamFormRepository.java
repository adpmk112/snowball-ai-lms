package com.ace.ai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.ExamForm;

@Repository
public interface ExamFormRepository extends JpaRepository<ExamForm, Integer>{    
    @Query(value = "select max(id) from exam_form", nativeQuery = true)
    public int findCurrentId();
}
