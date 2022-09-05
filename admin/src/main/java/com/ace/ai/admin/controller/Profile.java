package com.ace.ai.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class Profile {
    @GetMapping("admin/profile/")
    public String adminProfile(){

       return "A005";
    }
    @GetMapping("teacher/profile/")
    public String teacherProfile(){

        return "T006";
    }
}
