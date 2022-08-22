package com.ace.ai.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ace.ai.admin.service.AttendanceService;

@Controller
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/attendance")
    public String attendanceView(){
        attendanceService.showAttendanceTable(1);
        return "A001";
    }
}
