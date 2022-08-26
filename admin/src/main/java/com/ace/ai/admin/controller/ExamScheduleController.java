package com.ace.ai.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.dtomodel.ExamScheduleDTO;
import com.ace.ai.admin.service.ExamScheduleService;

@Controller
public class ExamScheduleController {
    @Autowired
    ExamScheduleService examScheduleService;

    /**
     * @param id
     * @param modelMap
     * @return
     */
    @GetMapping("/examSchedule")
    public ModelAndView classroomView(@RequestParam("id")Integer id, ModelMap modelMap){
        modelMap.addAttribute("batch_id", id);
        List<ExamScheduleDTO>examScheduleList = examScheduleService.showExamScheduleTable(id);
        modelMap.addAttribute("examScheduleList", examScheduleList);
        return new ModelAndView("A003-03","examScheduleDto",new ExamScheduleDTO());
    }
}
