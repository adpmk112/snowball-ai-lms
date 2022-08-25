package com.ace.ai.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @GetMapping(value = "/exam")
    public String getMethodName() {
        return "A002-05";
    }

    // Save Exam form
    @PostMapping(value = "/exam")
    public String saveExam(@RequestBody ExamDTO examDTO) {
        examFormService.saveExam(examDTO);
        return "redirect:/exam";
    }

    // Show Update Form
    @GetMapping("/exam-update/{id}")
    public String getExamToUpdate(@PathVariable("id") int id, Model model) {
        ExamForm examForm = examFormService.findById(id);
        ExamDTO exam = examFormService.getExamDTOFromExamForm(examForm);
        model.addAttribute("exam", exam);
        return "A002-06";
    }

    // Update Exam
    @PostMapping("/exam-update/{id}")
    public String updateExam(@PathVariable("id") int id, @RequestBody ExamDTO examDTO) {
        examFormService.updateExam(examDTO);
        return "A002-06";
    }

}
