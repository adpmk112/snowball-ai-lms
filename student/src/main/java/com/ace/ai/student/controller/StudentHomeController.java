package com.ace.ai.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ai.student.service.StudentCourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/student")
public class StudentHomeController {
    @Autowired
    StudentCourseService studentCourseService;

    @GetMapping(value="/home")
    public ModelAndView getStudentHome(@RequestParam String param) {
        return new ModelAndView();
    }
    
    
}
