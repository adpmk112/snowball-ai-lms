package com.ace.ai.admin.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.TeacherDashboardService;
import com.ace.ai.admin.service.TeacherService;



@Controller
@RequestMapping("/teacher")
public class TeacherDashboardController {

    @Autowired
    TeacherDashboardService teacherDashboardService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    BatchService batchService;

    @GetMapping("/home")
    public String teacherDashboard(ModelMap model){
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String teacherName = authentication.getName();
        // List<Batch> batchList = batchService.findBatchesByTeacherCode(teacherName);
        // model.addAttribute("batchList" , batchList);
        return "T001";
    }
}
