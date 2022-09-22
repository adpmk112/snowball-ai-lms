package com.ace.ai.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ace.ai.admin.dtomodel.CustomAssignmentDTO;
import com.ace.ai.admin.service.AssignmentService;

@Controller
@RequestMapping(value = "/teacher/batch")
public class AssignmentController {
    @Autowired
    AssignmentService assignmentService;

    @PostMapping("/customActivity/add")
    public String createCustomAssignment(@ModelAttribute("customAssignmentDTO") CustomAssignmentDTO customAssignmentDTO,
        @RequestParam("chapterId")int chapterId, @RequestParam("batchId")int batchId){
            assignmentService.customAssignmentAdd(customAssignmentDTO, batchId, chapterId);
            return "redirect:/teacher/batch/batchSeeMore?batchId="+batchId+"&radio=chapter";
    }
}
