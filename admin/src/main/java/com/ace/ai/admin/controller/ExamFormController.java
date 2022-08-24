package com.ace.ai.admin.controller;

import org.apache.tomcat.util.buf.Asn1Writer;
import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ace.ai.admin.datamodel.Answer;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Question;
import com.ace.ai.admin.dtomodel.ExamDTO;
import com.ace.ai.admin.dtomodel.QuestionDTO;
import com.ace.ai.admin.service.AnswerService;
import com.ace.ai.admin.service.ExamFormService;
import com.ace.ai.admin.service.QuestionService;

import java.util.*;


@Controller
public class ExamFormController {

    @Autowired
    ExamFormService examFormService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @GetMapping(value="/exam")
    public String getMethodName() {
        return "A002-05";
    }

    //Save Exam form
    @PostMapping(value="/saveExam")
    public String saveExam(@RequestBody ExamDTO examDTO) {
       
        String name = examDTO.getName();
        String course_id = examDTO.getCourse_id();
        String type = examDTO.getType();
        String duration = examDTO.getDuration();
        int maxMark = examDTO.getTotal_point();
        List<QuestionDTO> questionDTOs = examDTO.getQuestions();
        
        //save exam form 
        Course course = new Course();
        course.setId(Integer.valueOf(course_id));
        ExamForm examForm = new ExamForm();

        examForm.setName(name);
        examForm.setType(type);
        examForm.setDuration(duration);
        examForm.setMaxMark(maxMark);
        examForm.setCourse(course);
        examForm.setDeleteStatus(false);
        examFormService.saveExam(examForm);

        int exam_id = examFormService.findCurrentId();
        System.out.println("exam id is " + exam_id);
        examForm.setId(1);

        //save Question For Multiple Choice
        //int q_id = 1; 
        if (type.equals("Multiple Choice")){
            //System.out.println(questionDTOs.size());
            for(QuestionDTO questionDTO: questionDTOs){
                Question question = new Question();
                question.setName(questionDTO.getText());
                question.setTrueAnswer(questionDTO.getCorrect_answer());
                question.setExamForm(examForm);
                question.setDeleteStatus(false);
                questionService.saveQuestion(question);
                int question_id = questionService.findCurrentId();
                question.setId(question_id);
                //save Answers
                Answer answer = new Answer();
                for(String ans : questionDTO.getAnswer_list()){
                    answer.setAnswer(ans);
                    answer.setDeleteStatus(false);
                    answer.setQuestion(question);
                    answerService.saveAnswer(answer);
                }
    
            }
        }else{ //Exam form for file upload
            for(QuestionDTO questionDTO: questionDTOs){
                Question question = new Question();
                question.setName(questionDTO.getText());
                int question_id = questionService.findCurrentId();
                question.setId(question_id);
                question.setExamForm(examForm);
                questionService.saveQuestion(question);
            }
        }
        return "redirect:/exam";       
       
    }



    @GetMapping("/exam/{id}")
    public String getExamToUpdate(@PathVariable("id") String id){
        
        return "A002-06";
    }
    
    
}
