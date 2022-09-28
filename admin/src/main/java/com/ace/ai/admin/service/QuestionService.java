package com.ace.ai.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Answer;
import com.ace.ai.admin.datamodel.Question;
import com.ace.ai.admin.repository.AnswerRepository;
import com.ace.ai.admin.repository.QuestionRepository;

@Service
public class QuestionService {
    @Autowired QuestionRepository questionRepository;

    @Autowired AnswerService answerService;

    @Autowired AnswerRepository answerRepository;

    public void saveQuestion(Question question){
        //try{
            questionRepository.save(question);
        // }catch(Exception e){
        //     System.out.println("Question Service Save Error.");
        // }
    }
    
    public Question findById(int id){
        return questionRepository.getById(id);
    }

    public List<Question> findAll(){
        return questionRepository.findAll();
    }

    public List<Question> findByExam_form_id(int exam_form_id){
        return questionRepository.findByExamFormId(exam_form_id);
    }

    public void deleteByExamId(int exam_id){
        //Delete data
        List<Question> questions = questionRepository.findByExamFormId(exam_id);       
        for(Question question: questions){
            questionRepository.delete(question);
        }       
        
    }
}
