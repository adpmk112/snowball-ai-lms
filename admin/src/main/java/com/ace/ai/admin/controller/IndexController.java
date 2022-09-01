package com.ace.ai.admin.controller;

import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @GetMapping({"/","/home"})
    public String index(){
        return "A001";
    }

    @GetMapping({"/goToHome"})
    public String backHome(){
        return "A001";
    }
//    @GetMapping({"/"})
//    public String firstIndex(){return "index";}
//
//    @GetMapping({"/admin/login"})
//    public String adminLogin(){
//        return "ALGN001";
//    }

}
