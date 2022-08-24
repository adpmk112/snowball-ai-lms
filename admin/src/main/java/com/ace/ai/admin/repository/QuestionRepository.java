package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{
    @Query(value = "select max(id) from question", nativeQuery = true)
    public int findCurrentId();

    public List<Question> findByExamFormId(int exam_form_id);

}

