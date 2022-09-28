package com.ace.ai.admin.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.ace.ai.admin.datamodel.Answer;
import com.ace.ai.admin.datamodel.Question;
import com.ace.ai.admin.repository.AnswerRepository;
import com.ace.ai.admin.service.AnswerService;

@SpringBootTest
public class AnswerServiceTest {

    @InjectMocks
    AnswerService answerService;

    @Mock
    AnswerRepository answerRepository;

    @Test
    public void saveAnswerTest(){
        Answer answer = Answer.builder().id(1).answer("Answer 1").build();
        answerService.saveAnswer(answer);
        verify(answerRepository,times(1)).save(answer);
    }

    @Test
    public void findByIdTest(){
        Answer answer = Answer.builder().id(1).answer("Answer 1").build();
        when(answerRepository.getById(1)).thenReturn(answer);
        Answer answer1 = answerService.findById(1);
        assertEquals("Answer 1", answer1.getAnswer());
    }

    @Test
    public void findAllTest(){
        Answer answer = Answer.builder().id(1).answer("Answer 1").build();
        Answer answer1 = Answer.builder().id(1).answer("Answer 1").build();
        List<Answer> answers = new ArrayList<>();
        answers.add(answer);
        answers.add(answer1);
        when(answerRepository.findAll()).thenReturn(answers);
        List<Answer> answerList = answerService.findAll();
        assertEquals(2, answerList.size());
    }

    @Test
    public void findCurrentIdTest(){
        when(answerRepository.findCurrentId()).thenReturn(1);
        int id = answerService.findCurrentId();
        assertEquals(1, id);
    }

    @Test
    public void findByQuestion_idTest(){
        Question question = Question.builder().id(1).build();
        Answer answer = Answer.builder().id(1).answer("Answer 1").question(question).build();
        Answer answer1 = Answer.builder().id(1).answer("Answer 1").question(question).build();
        List<Answer> answers = new ArrayList<>();
        answers.add(answer);
        answers.add(answer1);
        when(answerRepository.findByQuestionId(1)).thenReturn(answers);
        List<Answer> answerList = answerService.findByQuestion_id(1);
        assertEquals(2, answerList.size());
    }

}
