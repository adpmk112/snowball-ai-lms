package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ai.admin.datamodel.ExamForm;

public interface ExamFormRepository extends JpaRepository<ExamForm,Integer>{
    public List<ExamForm> findAllByCourseId(Integer courseId);
}
