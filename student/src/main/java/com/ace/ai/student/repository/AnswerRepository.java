package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{
    @Query(value = "select max(id) from answer", nativeQuery = true)
    public int findCurrentId();

    public List<Answer> findByQuestionId(int question_id);

    public void deleteByQuestionId(int question_id);

} 
