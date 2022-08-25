package com.ace.ai.admin.service;

import java.util.List;

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
    public List<Answer> findAll(){
        return answerRepository.findAll();
    }
    public int findCurrentId(){
        return answerRepository.findCurrentId();
    }
    public List<Answer> findByQuestion_id(int question_id){
        return answerRepository.findByQuestionId(question_id);
    }

}
