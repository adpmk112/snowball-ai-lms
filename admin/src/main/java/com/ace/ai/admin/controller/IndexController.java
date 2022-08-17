package com.ace.ai.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"/","/home"})
    public String index(){
        return "admin-batch-add-page";
    }
}
