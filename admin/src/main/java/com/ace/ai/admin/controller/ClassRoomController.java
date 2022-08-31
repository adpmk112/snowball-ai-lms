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
import com.ace.ai.admin.dtomodel.ReqClassroomDTO;
import com.ace.ai.admin.service.ClassRoomService;

@Controller
public class ClassRoomController {
    @Autowired
    ClassRoomService classRoomService;

    @GetMapping("/setupClassroomAdd/{batch_id}")
    public ModelAndView classroomAdd(Model model, @PathVariable("batch_id")Integer id){
        ReqClassroomDTO reqClassroomDTO = new ReqClassroomDTO();
        reqClassroomDTO.setBatchId(id);
        model.addAttribute("teacherList",classRoomService.fetchTeacherListForClassroom(id));
        return new ModelAndView("A003-06","reqClassroomDTO",reqClassroomDTO);
    }

    @PostMapping("/createClassroom")
    public String createClassroom(Error errors,@ModelAttribute("reqClassroomDTO") ReqClassroomDTO reqClassroomDTO) throws ParseException{
        ReqClassroomDTO reqClassroomDTO2 = new ReqClassroomDTO();
        reqClassroomDTO2.setDate(reqClassroomDTO.getDate());
        reqClassroomDTO2.setTeacherName(reqClassroomDTO.getTeacherName());
        reqClassroomDTO2.setLink(reqClassroomDTO.getLink());
        reqClassroomDTO2.setBatchId(reqClassroomDTO.getBatchId());
        reqClassroomDTO2.setStartTime(reqClassroomDTO.getStartTime());
        reqClassroomDTO2.setEndTime(reqClassroomDTO.getEndTime());

        classRoomService.createClassroom(reqClassroomDTO2);

        return "A003-06";
    }

    @GetMapping("/editClassroom")
    public String classroomEdit(){
        return "A003-07";
    }
}
