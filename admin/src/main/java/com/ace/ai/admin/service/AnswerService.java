package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Answer;
import com.ace.ai.admin.repository.AnswerRepository;

@Service
public class AnswerService {
    @Autowired
    AnswerRepository answerRepository;

    public void saveAnswer(Answer answer){
        // try{
            answerRepository.save(answer);
            System.out.println("Answer Service save success.");
        // }catch(Exception ex){
        //     System.out.println("Answer service save error.");
        // }
    }

    public Answer findById(int id){        
            return answerRepository.getById(id);           
    }
}
