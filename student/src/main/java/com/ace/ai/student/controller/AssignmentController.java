package com.ace.ai.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssignmentController {

    @GetMapping("/a")
    public String assignmentStudent(){
        return "S001-03";
    }
    
}
