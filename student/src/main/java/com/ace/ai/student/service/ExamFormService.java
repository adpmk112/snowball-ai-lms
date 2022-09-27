package com.ace.ai.student.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ace.ai.student.datamodel.Answer;
import com.ace.ai.student.datamodel.BatchExamForm;
import com.ace.ai.student.datamodel.ExamForm;
import com.ace.ai.student.datamodel.Question;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.datamodel.StudentExamMark;
import com.ace.ai.student.dtomodel.ExamDTO;
import com.ace.ai.student.dtomodel.QuestionDTO;
import com.ace.ai.student.repository.BatchExamFormRepository;
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
    BatchExamFormRepository batchExamFormRepository;

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
        int batchId = studentRepository.getById(studentId).getBatch().getId();//get batch id 
        BatchExamForm bef = batchExamFormRepository.findByBatch_IdAndExamForm_Id(batchId, examId);
        int batchExamId = bef.getId();
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
        return new ExamDTO(batchExamId,studentId, name, type, duration, questionDTO_list, totalPoint);
    }
    
    //Save student answers for multiple choice questions
    public void saveAnswerAsMultipleChoice(ExamDTO examDTO){
        int studentId = examDTO.getStudentId();
        int batchExamId = examDTO.getId();
        int studentTotalMark = 0;
        Student student = studentRepository.getById(studentId);
        BatchExamForm batchExamForm = batchExamFormRepository.getById(batchExamId);
        List<QuestionDTO> questionDTOs = examDTO.getQuestions();
        for(QuestionDTO question: questionDTOs){
            if(question.getStudentAnswer().equals(question.getCorrectAnswer())){
                studentTotalMark += question.getPoint();
            }
        }
        StudentExamMark studentExamMark = new StudentExamMark(studentTotalMark,student,batchExamForm);
        studentExamMark.setNotification(false);
        //For Now Date
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = now.format(formatter);
        studentExamMark.setAnswerDate(formattedString);
        studentExamMarkService.save(studentExamMark);
    }

    //Save student Asnwers for file upload question
    public void saveAnswerAsFileUpload(ExamDTO examDTO) throws IOException{
        int studentId = examDTO.getStudentId();
        int batchExamId = examDTO.getId();
        int studentTotalMark = 0;
        Student student = studentRepository.getById(studentId);
        BatchExamForm batchExamForm = batchExamFormRepository.getById(batchExamId);
        String uploadDIR = "./assets/img/examAnswers/"+batchExamForm.getExamForm().getId()+"/"+studentId+"/";// path is assets/examAnswers/examId/studentId/name
        MultipartFile answerFile = examDTO.getAnswerFile();
         //Save filename to database
        StudentExamMark studentExamMark = new StudentExamMark();       
        studentExamMark.setBatchExamForm(batchExamForm);
        studentExamMark.setStudent(student);
        studentExamMark.setStudentMark(studentTotalMark); 
         //For Now Date
         LocalDateTime now = LocalDateTime.now();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
         String formattedString = now.format(formatter);
         studentExamMark.setAnswerDate(formattedString);  
         studentExamMark.setNotification(false);     
        if(!answerFile.isEmpty() && answerFile != null){
                        
            String studentName = student.getName().trim().replaceAll("\\s","-");
            //Get Now Date
            // LocalDate date = LocalDate.now();
            // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            // String nowDate = date.format(formatter);
            String studentData = studentName;
            String fileName = studentData+"-"+answerFile.getOriginalFilename();

            //save the file on the local file system
            Path path = Paths.get(uploadDIR + fileName);
            if(!Files.exists(path)){
                Files.createDirectories(path);
            }
            Files.copy(answerFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            studentExamMark.setNotification(true);// set notification if exists
            studentExamMark.setUploadedFile(fileName);//Set file if exists          
            
        }
        studentExamMarkService.save(studentExamMark);
    }
}
