package com.ace.ai.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ace.ai.admin.datamodel.Answer;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Question;
import com.ace.ai.admin.dtomodel.ExamDTO;
import com.ace.ai.admin.dtomodel.QuestionDTO;
import com.ace.ai.admin.service.AnswerService;
import com.ace.ai.admin.service.ExamFormService;
import com.ace.ai.admin.service.QuestionService;

import java.io.Console;
import java.util.*;

@Controller
public class ExamFormController {

    @Autowired
    ExamFormService examFormService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @GetMapping(value = "/admin-exam")
    public String getMethodName(@RequestParam("courseId") int courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "A002-05";
    }

    //Check Exam Exists According to Coruse 
    @GetMapping("/checkExamName")
    @ResponseBody
    public ResponseEntity isExamExist(@RequestParam("examName") String examName, @RequestParam("courseId") int courseId ){
        ExamForm exam = examFormService.findByNameAndCourseId(examName, courseId);
        System.out.println("Exam is "+exam);
        if(exam != null){
            return ResponseEntity.ok(HttpStatus.OK);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

      // Show All Exam List
      @GetMapping("/admin-exam-list")
      public String showAllExam(Model model){
          int courseId = 2;
          List<ExamForm> exams = examFormService.findByDeleteStatusAndCourseId(false, courseId);
          model.addAttribute("radioButton", "exam");
          model.addAttribute("examList", exams);
          model.addAttribute("courseId", courseId);
          return "A002-01 copy";
      }

    // Save Exam form
    @PostMapping(value = "/admin-exam")
    public String saveExam(@RequestBody ExamDTO examDTO ) {
        examFormService.saveExam(examDTO);
        return "redirect:/admin-exam";
    }

    // Show Update Form
    @GetMapping("/admin-exam-update/{id}")
    public String getExamToUpdate(@PathVariable("id") int id, Model model) {
        ExamForm examForm = examFormService.findById(id);
        ExamDTO exam = examFormService.getExamDTOFromExamForm(examForm);
        model.addAttribute("exam", exam);
        return "A002-06";
    }

    // Update Exam
    @PostMapping("/admin-exam-update/{id}")
    public String updateExam(@PathVariable("id") int id, @RequestBody ExamDTO examDTO) {
        examFormService.updateExam(examDTO);
        return "A002-06";
    }

  

}
