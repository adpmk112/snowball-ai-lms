package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.ExamForm;

@Repository
public interface ExamFormRepository extends JpaRepository<ExamForm, Integer>{    
    @Query(value = "select max(id) from exam_form", nativeQuery = true)
    public int findCurrentId();

    public List<ExamForm> findByCourse_id(Integer courseId);

    public ExamForm findByNameAndCourse_Id(String name, int id);

    public List<ExamForm> findByDeleteStatusAndCourse_Id(Boolean status, int course_id);
}
