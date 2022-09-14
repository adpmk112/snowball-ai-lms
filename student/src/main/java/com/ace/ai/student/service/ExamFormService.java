package com.ace.ai.student.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Answer;
import com.ace.ai.student.datamodel.ExamForm;
import com.ace.ai.student.datamodel.Question;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.datamodel.StudentExamMark;
import com.ace.ai.student.dtomodel.ExamDTO;
import com.ace.ai.student.dtomodel.QuestionDTO;
import com.ace.ai.student.repository.ExamFormRepository;
import com.ace.ai.student.repository.StudentRepository;

@Service
public class ExamFormService {

    @Autowired
    ExamFormRepository examFormRepo;

    @Autowired StudentRepository studentRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    StudentExamMarkService studentExamMarkService;


    public ExamForm findById(int id) {
        return examFormRepo.getById(id);
    }

    public int findCurrentId() {
        return examFormRepo.findCurrentId();
    }

    public List<ExamForm> findAll() {
        return examFormRepo.findAll();
    }

    public List<ExamForm> findByCourseId(int course_id) {
        return examFormRepo.findByCourse_id(course_id);
    }

    public int findByNameAndCourseId(String name, int id){
        return examFormRepo.findByNameAndCourse_Id(name, id);
    }

    public List<ExamForm> findByDeleteStatusAndCourseId(Boolean status, int course_id){
        return examFormRepo.findByDeleteStatusAndCourse_Id(status, course_id);
    } 
    
    public ExamDTO getExamDTO(int examId, int studentId) {
        ExamForm exam = examFormRepo.getById(examId);
        String name = exam.getName();
        String type = exam.getType();
        String duration = exam.getDuration();
        int totalPoint = exam.getMaxMark();
        List<Question> question_list = questionService.findByExam_form_id(exam.getId());
        List<QuestionDTO> questionDTO_list = new ArrayList<QuestionDTO>();
        for (Question question : question_list) {
            List<Answer> answer_list = answerService.findByQuestion_id(question.getId());
            List<String> answerList = new ArrayList<>();
            for (Answer answer : answer_list) {
                answerList.add(answer.getAnswer());
            }
            QuestionDTO questionDTO = new QuestionDTO(question.getId(), question.getName(), answerList,
                    question.getTrueAnswer(), question.getPoint(), "");
            questionDTO_list.add(questionDTO);
        }
        return new ExamDTO(examId,studentId, name, type, duration, questionDTO_list, totalPoint,"no answer");
    }
    
    public void saveAnswerAsMultipleChoice(ExamDTO examDTO){
        int studentId = examDTO.getStudentId();
        int examId = examDTO.getId();
        int studentTotalMark = 0;
        Student student = studentRepository.getById(studentId);
        ExamForm examForm = examFormRepo.getById(examId);
        List<QuestionDTO> questionDTOs = examDTO.getQuestions();
        for(QuestionDTO question: questionDTOs){
            if(question.getStudentAnswer().equals(question.getCorrectAnswer())){
                studentTotalMark += question.getPoint();
            }
        }
        StudentExamMark studentExamMark = new StudentExamMark(studentTotalMark,student,examForm);
        studentExamMarkService.save(studentExamMark);
    }
}
