package com.ace.ai.student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LiveSessionController {

    @GetMapping({"/livesession"})
    public String liveSession(){
        return "livesession";
    }
}
