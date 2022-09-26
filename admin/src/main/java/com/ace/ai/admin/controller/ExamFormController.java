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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ace.ai.admin.datamodel.Answer;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Question;
import com.ace.ai.admin.dtomodel.AdminChapterDTO;
import com.ace.ai.admin.dtomodel.ExamDTO;
import com.ace.ai.admin.dtomodel.QuestionDTO;
import com.ace.ai.admin.service.*;
import com.ace.ai.admin.service.ExamFormService;
import com.ace.ai.admin.service.QuestionService;

import java.io.Console;
import java.util.*;

@Controller
@RequestMapping(value = "/admin/course")
public class ExamFormController {

    @Autowired
    ExamFormService examFormService;

    @Autowired
    QuestionService questionService;

    @Autowired
    AnswerService answerService;

    @Autowired
    CourseService courseService;

    // Check Exam Exists According to Coruse
    @GetMapping("/checkExamName")
    @ResponseBody
    public ResponseEntity isExamExist(@RequestParam("examName") String examName, @RequestParam("courseId") int courseId) {
        int exam_count = examFormService.findByNameAndCourseId(examName, courseId);
        if (exam_count > 0) {
            return ResponseEntity.ok(HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }    

    // Cancel Exam
    @GetMapping("/exam-cancel")
    public String cancelExam(@RequestParam("courseId") int courseId) {
        return "redirect:/admin/course/courseDetail?radio=exam&courseId=" + courseId;
    }

    // Show Exam Form
    @GetMapping(value = "/exam-save")
    public String getMethodName(@RequestParam("courseId") int courseId, Model model) {
        Course course = courseService.getById(courseId);
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName",course.getName());
        return "A002-05";
    }

    // Save Exam form
    @PostMapping(value = "/exam-save")
    public String saveExam(@RequestBody ExamDTO examDTO) {
        examFormService.saveExam(examDTO);
        int courseId = Integer.valueOf(examDTO.getCourse_id());
        return "redirect:/admin/course/courseDetail?radio=exam&courseId=" + courseId;
    }

    // Show Update Form
    @GetMapping("/exam-update")
    public String getExamToUpdate(@RequestParam("id") int id, Model model, @RequestParam("courseId") int courseId) {
        ExamForm examForm = examFormService.findById(id);
        ExamDTO exam = examFormService.getExamDTOFromExamForm(examForm);
        Course course = courseService.getById(courseId);
        model.addAttribute("exam", exam);
        model.addAttribute("courseId", courseId);
        model.addAttribute("courseName",course.getName());
        return "A002-06";
    }

    // Update Exam
    @PostMapping("/exam-update/{id}")
    public String updateExam(@PathVariable("id") int id, @RequestBody ExamDTO examDTO) {
        examFormService.updateExam(examDTO);
        int courseId = Integer.valueOf(examDTO.getCourse_id());
        return "redirect:/admin/course/courseDetail?radio=exam&courseId=" + courseId;
    }

    //Delete Exam
    @GetMapping("/exam-delete")
    public String deleteExam(@RequestParam("id")int id,@RequestParam("courseId") int courseId) {
        // ExamForm exam = examFormService.findById(id);
        // exam.setDeleteStatus(true);
        examFormService.deleteExamForm(id);
        return "redirect:/admin/course/courseDetail?radio=exam&courseId=" + courseId;
    }
    

}
