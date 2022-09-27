package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.ExamForm;

@Repository
public interface ExamFormRepository extends JpaRepository<ExamForm, Integer>{    
    
    public List<ExamForm> findByCourse_id(Integer courseId);

    @Query(value = "select count(id) from exam_form where name=?1 and course_id = ?2", nativeQuery = true)
    public int findByNameAndCourse_Id(String name, int id);

    public List<ExamForm> findByDeleteStatusAndCourse_Id(Boolean status, int course_id);

    public ExamForm findByDeleteStatusAndId(boolean deleteStatus, int id);

}
