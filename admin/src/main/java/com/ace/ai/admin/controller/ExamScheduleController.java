package com.ace.ai.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ace.ai.admin.service.ExamScheduleService;

@Controller
public class ExamScheduleController {
    @Autowired
    ExamScheduleService examScheduleService;

    @GetMapping("/examSchedule")
    public String classroomView(){
        examScheduleService.showExamScheduleTable(1);
        return "A001";
    }
}
