package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Question;
import com.ace.ai.admin.repository.QuestionRepository;

@Service
public class QuestionService {
    @Autowired QuestionRepository questionRepository;

    public void saveQuestion(Question question){
        //try{
            questionRepository.save(question);
            System.out.println("Question Saved Successfylly.");
        // }catch(Exception e){
        //     System.out.println("Question Service Save Error.");
        // }
    }
    
    public Question findById(int id){
        return questionRepository.getById(id);
    }

    public int findCurrentId(){
        return questionRepository.findCurrentId();
    }
}
