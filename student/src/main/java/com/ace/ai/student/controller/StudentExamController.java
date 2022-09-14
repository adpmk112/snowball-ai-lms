package com.ace.ai.student.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.BatchExamForm;
import com.ace.ai.student.datamodel.ExamForm;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.dtomodel.ExamDTO;
import com.ace.ai.student.dtomodel.QuestionDTO;
import com.ace.ai.student.dtomodel.StudentExamDoneDTO;
import com.ace.ai.student.repository.StudentRepository;
import com.ace.ai.student.service.BatchExamFormService;
import com.ace.ai.student.service.ExamFormService;

@Controller
public class StudentExamController {
    @Autowired BatchExamFormService batchExamFormService;
    @Autowired StudentRepository studentRepository;
    @Autowired ExamFormService examFormService;
     

    @GetMapping("/exam")
    public String showExamPage(Model model, @RequestParam("studentId") int studentId){
        int batchId = studentRepository.getById(studentId).getBatch().getId();
        List<ExamForm> upcomingExamList = batchExamFormService.getUpcomingExamList(batchId);
        List<StudentExamDoneDTO> finishedExamList = batchExamFormService.getFinishedExamList(batchId, studentId);
        model.addAttribute("upcomingExamList", upcomingExamList);
        model.addAttribute("finishedExamList",finishedExamList );
        model.addAttribute("studentId", studentId);
        return "S003";
    }

    @GetMapping("/showExamForm")
    public String showExamForm(Model model, @RequestParam("studentId") int studentId, @RequestParam("examId") int examId){
        ExamDTO exam = examFormService.getExamDTO(examId, studentId);        
        model.addAttribute("exam", exam);
        return "S003-01";
    }

    @PostMapping("/studentExamAnswer")
    public String studentExamAnswer(@ModelAttribute("exam") ExamDTO examDTO){
        int studentId = examDTO.getStudentId();
        String type = examDTO.getType();
        if(type.equalsIgnoreCase("multiple choice")){
            examFormService.saveAnswerAsMultipleChoice(examDTO);
        }
        System.out.println(examDTO.getAnswerFile());
        return "redirect:/exam?studentId="+studentId;
    }
}
