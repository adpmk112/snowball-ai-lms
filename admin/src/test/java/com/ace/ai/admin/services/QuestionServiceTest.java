package com.ace.ai.admin.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Question;
import com.ace.ai.admin.repository.QuestionRepository;
import com.ace.ai.admin.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class QuestionServiceTest {
    @Mock
    QuestionRepository questionRepository;

    @InjectMocks
    QuestionService questionService;

    @Test
    public void saveQuestionTest(){
        Question question = Question.builder().id(1).name("Question 1").build();
        questionService.saveQuestion(question);
        verify(questionRepository, times(1)).save(question);
    }

    @Test
    public void findByIdTest(){
        Question question = Question.builder().id(1).name("Question 1").build();
        when(questionRepository.getById(1)).thenReturn(question);
        Question question1 = questionService.findById(1);
        assertEquals("Question 1", question1.getName());
    }

    @Test
    public void findAllTest(){
        Question question1 = Question.builder().id(1).name("Question 1").build();
        Question question2 = Question.builder().id(2).name("Question 2").build();
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        when(questionRepository.findAll()).thenReturn(questions);
        List<Question> list1 = questionService.findAll();
        assertEquals(2, list1.size());
    }

    @Test
    public void findByExam_form_idTest(){
        ExamForm exam = ExamForm.builder().id(1).name("Final Exam").build();
        Question question1 = Question.builder().id(1).name("Question 1").examForm(exam).build();
        Question question2 = Question.builder().id(2).name("Question 2").examForm(exam).build();
        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        when(questionRepository.findByExamFormId(1)).thenReturn(questions);
        List<Question> list1 = questionService.findByExam_form_id(1);
        assertEquals(2, list1.size());
    }

    @Test
    public void deleteByExamId(){
        ExamForm exam = ExamForm.builder().id(1).name("Final Exam").build();
        Question question1 = Question.builder().id(1).name("Question 1").examForm(exam).build();
        questionService.deleteByExamId(exam.getId());
        verify(questionRepository,times(1)).delete(question1);
    }
}
