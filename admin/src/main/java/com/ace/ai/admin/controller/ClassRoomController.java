package com.ace.ai.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ace.ai.admin.service.ClassRoomService;

@Controller
public class ClassRoomController {
    @Autowired
    ClassRoomService classRoomService;

    @GetMapping("/addClassroom")
    public String classroomAdd(){
        return "A003-06";
    }

    @GetMapping("/editClassroom")
    public String classroomEdit(){
        return "A003-07";
    }
}
