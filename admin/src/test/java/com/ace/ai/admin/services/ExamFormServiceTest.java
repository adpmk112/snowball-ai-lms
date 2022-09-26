package com.ace.ai.admin.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.dtomodel.ExamDTO;
import com.ace.ai.admin.dtomodel.QuestionDTO;
import com.ace.ai.admin.repository.ExamFormRepository;
import com.ace.ai.admin.service.AnswerService;
import com.ace.ai.admin.service.ExamFormService;
import com.ace.ai.admin.service.QuestionService;

@SpringBootTest
public class ExamFormServiceTest {
    
    @Mock
    ExamFormRepository examFormRepository;

    @Mock
    QuestionService questionService;

    @Mock
    AnswerService answerService;

    @InjectMocks
    ExamFormService examFormService;

    @Test
    public void saveExamTest(){
        List<QuestionDTO> questionDTO = new ArrayList<>();
        ExamDTO examDTO = new ExamDTO("1","1","ExamName","Multiple Choice","1:30",questionDTO,"30");
        String exam_id = examDTO.getId();
        String name = examDTO.getName();
        String course_id = examDTO.getCourse_id();
        String type = examDTO.getType();
        String duration = examDTO.getDuration();
        String maxMark = examDTO.getTotal_point();
        // save exam form
        Course course = new Course();
        course.setId(Integer.valueOf(course_id));
        ExamForm examForm = new ExamForm();
        if(exam_id == null){
            examForm.setName(name);
            examForm.setType(type);
            examForm.setDuration(duration);
            examForm.setMaxMark(Integer.valueOf(maxMark));
            examForm.setDeleteStatus(false);
            examForm.setCourse(course);              
        }else{// This is for update             
            examForm.setId(Integer.valueOf(exam_id));                         
            examForm.setName(name);
            examForm.setType(type);
            examForm.setDuration(duration);
            examForm.setMaxMark(Integer.valueOf(maxMark));
            examForm.setDeleteStatus(false);
            examForm.setCourse(course);  
        }
        examFormService.saveExam(examDTO);
        verify(examFormRepository,times(1)).save(examForm);                

    }

    @Test
    public void updateExamTest(){
        List<QuestionDTO> questionDTO = new ArrayList<>();
        ExamDTO examDTO = new ExamDTO("1","1","ExamName","Multiple Choice","1:30",questionDTO,"30");
        examFormService.updateExam(examDTO);
        //verify(examFormRepository,times(1)).save(entity);
    }

    @Test
    public void saveByJPaTest(){ 
        List<BatchExamForm> batchExamForms = new ArrayList<>();
        batchExamForms.add(new BatchExamForm());
        ExamForm examForm = new ExamForm(1,"Name","Type","1:20",50,false,new Course());
        examFormService.saveByJPa(examForm);
        verify(examFormRepository, times(1)).save(examForm);
    }
}
