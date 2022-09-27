package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Answer;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Question;
import com.ace.ai.admin.dtomodel.ExamDTO;
import com.ace.ai.admin.dtomodel.QuestionDTO;
import com.ace.ai.admin.repository.ExamFormRepository;

@Service
public class ExamFormService {

    @Autowired
    ExamFormRepository examFormRepo;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;


    public void saveExam(ExamDTO examDTO) {
        try {
            String exam_id = examDTO.getId();
            String name = examDTO.getName();
            String course_id = examDTO.getCourse_id();
            String type = examDTO.getType();
            String duration = examDTO.getDuration();
            String maxMark = examDTO.getTotal_point();
            List<QuestionDTO> questionDTOs = examDTO.getQuestions();

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
                    examFormRepo.save(examForm);


            // save Question For Multiple Choice
            if (type.equals("Multiple Choice")) {
                for (QuestionDTO questionDTO : questionDTOs) {                    
                    Question question = new Question(
                            questionDTO.getText(),
                            questionDTO.getCorrect_answer(),
                            false,
                            examForm,
                            Integer.valueOf(questionDTO.getPoint()));
                    questionService.saveQuestion(question);                   
                    // save Answers
                    for (String ans : questionDTO.getAnswer_list()) {
                        System.out.println(ans);                        
                        Answer answer = new Answer( ans, false, question);
                        answerService.saveAnswer(answer);
                    }

                }
            } else { // Exam form for file upload
                for (QuestionDTO questionDTO : questionDTOs) {
                   
                    Question question = new Question(
                            questionDTO.getText(),
                            questionDTO.getCorrect_answer(),
                            false,
                            examForm,
                            Integer.valueOf(questionDTO.getPoint()));
                    questionService.saveQuestion(question);
                }
            }
            // examFormRepo.save(exam);
        } catch (Exception ex) {
            System.out.println("ExamForm save Error.");
        }
    }

    public void updateExam(ExamDTO examDTO){
        //int id = Integer.valueOf(examDTO.getId());
        //delete all data
        questionService.deleteByExamId(Integer.valueOf(examDTO.getId()));
        //update ExamFrom        
        saveExam(examDTO);
    }

    public void saveByJPa(ExamForm exam){
        examFormRepo.save(exam);
    }

    public ExamForm findById(int id) {
        return examFormRepo.getById(id);
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
  
    public ExamDTO getExamDTOFromExamForm(ExamForm exam) {
        int id = exam.getId();
        String name = exam.getName();
        String type = exam.getType();
        String duration = exam.getDuration();
        String total_point = String.valueOf(exam.getMaxMark());
        List<Question> question_list = questionService.findByExam_form_id(exam.getId());

        List<QuestionDTO> questionDTO_list = new ArrayList<QuestionDTO>();
        for (Question question : question_list) {
            List<Answer> answer_list = answerService.findByQuestion_id(question.getId());
            List<String> answerList = new ArrayList<>();
            for (Answer answer : answer_list) {
                answerList.add(answer.getAnswer());
            }
            QuestionDTO questionDTO = new QuestionDTO(question.getId(), question.getName(), answerList,
                    question.getTrueAnswer(), String.valueOf(question.getPoint()));
            questionDTO_list.add(questionDTO);
        }
        return new ExamDTO(String.valueOf(id), name, type, duration, questionDTO_list, total_point);
    }

    public void deleteExamForm(int examId){
        examFormRepo.deleteById(examId);
    }
}
