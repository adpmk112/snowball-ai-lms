package com.ace.ai.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BatchController {

    @GetMapping({"/A003"})
    public String gotoBatch(){
        return "A003";
    }


    @GetMapping({"/A003-03"})
    public String seeMore(){
        return "A003-03";
    }

}
