package com.ace.ai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Integer>{
    
}
