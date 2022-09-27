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

import com.ace.ai.admin.datamodel.Answer;
import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Question;
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

    // @Test
    // public void saveExamTest(){
    //     List<QuestionDTO> questionDTO = new ArrayList<>();
    //     ExamDTO examDTO = new ExamDTO("1","1","ExamName","Multiple Choice","1:30",questionDTO,"30");
    //     String exam_id = examDTO.getId();
    //     String name = examDTO.getName();
    //     String course_id = examDTO.getCourse_id();
    //     String type = examDTO.getType();
    //     String duration = examDTO.getDuration();
    //     String maxMark = examDTO.getTotal_point();
    //     //save exam form
    //     Course course = new Course();
    //     course.setId(Integer.valueOf(course_id));
    //     ExamForm examForm = ExamForm.builder().id(100).name(name).course(course).build();
    //     if(exam_id == null){
    //         examForm.setName(name);
    //         examForm.setType(type);
    //         examForm.setDuration(duration);
    //         examForm.setMaxMark(Integer.valueOf(maxMark));
    //         examForm.setDeleteStatus(false);
    //         examForm.setCourse(course);              
    //     }else{// This is for update             
    //         examForm.setId(Integer.valueOf(exam_id));                         
    //         examForm.setName(name);
    //         examForm.setType(type);
    //         examForm.setDuration(duration);
    //         examForm.setMaxMark(Integer.valueOf(maxMark));
    //         examForm.setDeleteStatus(false);
    //         examForm.setCourse(course);  
    //     }
    //    examFormService.saveExam(examDTO);
    //    verify(examFormRepository, times(1)).save(examForm);
    // }

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

    @Test
    public void findByIdTest(){
        ExamForm exam = ExamForm.builder().id(1).name("Final Exam").type("Multiple Choice").duration("1:30").build();
        when(examFormRepository.getById(1)).thenReturn(exam);
        ExamForm exam1 = examFormService.findById(1);
        assertEquals("Final Exam", exam1.getName());
    }

    @Test
    public void findAllTest(){
        List<ExamForm> list = new ArrayList<>();
        ExamForm exam1 = ExamForm.builder().id(1).name("Mid Term Exam").build();
        ExamForm exam2 = ExamForm.builder().id(2).name("Final Exam").build();
        list.add(exam1);
        list.add(exam2);
        when(examFormRepository.findAll()).thenReturn(list);
        List<ExamForm> listFormService = examFormService.findAll();
        assertEquals(2, listFormService.size());

    }

    @Test
    public void findByCourseIdTest(){
        Course course = new Course();
        course.setId(1);
        course.setName("Java");
        List<ExamForm> list = new ArrayList<>();
        ExamForm exam1 = ExamForm.builder().id(1).name("Mid Term Exam").course(course).build();
        ExamForm exam2 = ExamForm.builder().id(2).name("Final Exam").course(course).build();
        list.add(exam1);
        list.add(exam2);
        when(examFormRepository.findByCourse_id(1)).thenReturn(list);
        List<ExamForm> list1 = examFormService.findByCourseId(1);
        assertEquals(2, list1.size());
    }

    @Test
    public void findByDeleteStatusAndCourseIdTest(){
        Course course = new Course();
        course.setId(1);
        course.setName("Java");
        List<ExamForm> list = new ArrayList<>();
        ExamForm exam1 = ExamForm.builder().id(1).name("Mid Term Exam").course(course).deleteStatus(false).build();
        ExamForm exam2 = ExamForm.builder().id(2).name("Final Exam").course(course).deleteStatus(false).build();
        list.add(exam1);
        list.add(exam2);
        when(examFormRepository.findByDeleteStatusAndCourse_Id(false, 1)).thenReturn(list);
        List<ExamForm> list1 = examFormService.findByDeleteStatusAndCourseId(false,1);
        assertEquals(2, list1.size());
    }   

    @Test
    public void findByNameAndCourseIdTest(){
        Course course = new Course();
        course.setId(1);
        course.setName("Java");
        int count = 1;
        when(examFormRepository.findByNameAndCourse_Id("Exam", 1)).thenReturn(count);
        int count1 = examFormService.findByNameAndCourseId("Exam",1);
        assertEquals(1, count1);
    }

    @Test
    public void getExamDgetExamDTOFromExamFormTest(){
        Course course = new Course();
        course.setId(1);
        course.setName("Java");
        List<Answer> answerList = new ArrayList<>();
        Answer answer = Answer.builder().id(1).answer("answer").build();
        answerList.add(answer);
        List<Question> questionList = new ArrayList<>();
        Question question1 = Question.builder().id(1).name("question1").answers(answerList).trueAnswer("trueAnswer").point(1).build();
        Question question2 = Question.builder().id(1).name("question2").answers(answerList).trueAnswer("trueAnswer").point(1).build();
        questionList.add(question1);
        questionList.add(question2);
        ExamForm exam = ExamForm.builder().id(1).name("Mid Term Exam").type("MC").duration("1:30").maxMark(100).course(course).questions(questionList).build();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //when(questionService.findByExam_form_id(1)).thenReturn(questionList);
        for(Question question: exam.getQuestions()){            
            //when(answerService.findByQuestion_id(1)).thenReturn(answerList);
            List<String> ansList = new ArrayList<>();
            for(Answer ans: question.getAnswers()){
                ansList.add(ans.getAnswer());
            }
            QuestionDTO qDTO= new QuestionDTO(question.getId(), question.getName(), ansList,
            question.getTrueAnswer(), String.valueOf(question.getPoint()));
            questionDTOList.add(qDTO);
        
        }
        ExamDTO examDTO = ExamDTO.builder().id(String.valueOf(exam.getId())).name(exam.getName()).type(exam.getType()).duration(exam.getDuration()).questions(questionDTOList).total_point(String.valueOf(exam.getMaxMark())).build();
        ExamDTO testDTO = examFormService.getExamDTOFromExamForm(exam);
        assertEquals(examDTO.getId(), testDTO.getId());
    }

    @Test
    public void deleteExamFormTest(){
        examFormService.deleteExamForm(1);
        verify(examFormRepository,times(1)).deleteById(1);
    }

}
