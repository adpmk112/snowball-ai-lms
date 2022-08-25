package com.ace.ai.admin.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.CourseRepository;
import com.ace.ai.admin.repository.TeacherRepository;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/adminDashboard")
    public String adminDashboard(ModelMap model){
        List<Course> courseList=courseRepository.findByDeleteStatus(false);
        int courseCount = courseList.size();
        model.addAttribute("courseCount", courseCount);
        List<Batch> batchList=batchRepository.findByDeleteStatus(false);
        int batchCount = batchList.size();
        model.addAttribute("batchCount",batchCount);
        List<Teacher> teacherList=teacherRepository.findByDeleteStatus(false);
        int teacherCount = teacherList.size();
        model.addAttribute("teacherCount",teacherCount);
        return "A001";
    }
}
