package com.ace.ai.student.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping({"/student/login","/"})
    public String studentLogin(){
        return "SLGN001";
    }

    @GetMapping({"/student/logout"})
    public String studentLogout(){
        return "SLGN001";
    }

}
