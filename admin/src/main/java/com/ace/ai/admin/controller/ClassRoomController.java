package com.ace.ai.admin.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.dtomodel.ClassroomDTO;
import com.ace.ai.admin.service.ClassRoomService;

@Controller
public class ClassRoomController {
    @Autowired
    ClassRoomService classRoomService;

    @GetMapping("/setupClassroomAdd/{batch_id}")
    public ModelAndView classroomAdd(Model model, @PathVariable("batch_id")Integer id){
        ClassroomDTO classroomDTO = new ClassroomDTO();
        model.addAttribute("teacherList",classRoomService.fetchTeacherListForClassroom(id));
        return new ModelAndView("A003-06","classroomDTO",classroomDTO);
    }

    @PostMapping("/createClassroom")
    public String createClassroom(Error errors,@ModelAttribute("classroomDTO") ClassroomDTO classroomDTO) throws ParseException{
        ClassroomDTO classroomDTO2 = new ClassroomDTO();
        classroomDTO2.setDate(classroomDTO.getDate());
        classroomDTO2.setLink(classroomDTO.getLink());
        classroomDTO2.setBatchId(classroomDTO.getBatchId());
        classroomDTO2.setStartTime(classroomDTO.getStartTime());
        classroomDTO2.setEndTime(classroomDTO.getEndTime());

        classRoomService.createClassroom(classroomDTO2);

        return "redirect:/setupClassroomAdd/{batch_id}";
    }

    @GetMapping("/editClassroom")
    public String classroomEdit(){
        return "A003-07";
    }
}
