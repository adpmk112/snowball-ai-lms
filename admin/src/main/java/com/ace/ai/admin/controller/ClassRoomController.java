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

import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.dtomodel.ClassroomDTO;
import com.ace.ai.admin.dtomodel.ReqClassroomDTO;
import com.ace.ai.admin.service.ClassRoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ClassRoomController {
    @Autowired
    ClassRoomService classRoomService;

    @GetMapping("/setupClassroomAdd/{batch_id}")
    public ModelAndView classroomAdd(Model model, @PathVariable("batch_id") Integer id) {
        ReqClassroomDTO reqClassroomDTO = new ReqClassroomDTO();
        reqClassroomDTO.setBatchId(id);
        model.addAttribute("teacherList", classRoomService.fetchTeacherListForClassroom(id));
        return new ModelAndView("A003-06", "reqClassroomDTO", reqClassroomDTO);
    }

    @PostMapping("/createClassroom")
    public ModelAndView createClassroom(Model model, @ModelAttribute("reqClassroomDTO") ReqClassroomDTO reqClassroomDTO)
            throws ParseException {
        ReqClassroomDTO reqClassroomDTO2 = new ReqClassroomDTO();
        reqClassroomDTO2.setDate(reqClassroomDTO.getDate());
        reqClassroomDTO2.setTeacherName(reqClassroomDTO.getTeacherName());
        reqClassroomDTO2.setLink(reqClassroomDTO.getLink());
        reqClassroomDTO2.setBatchId(reqClassroomDTO.getBatchId());
        reqClassroomDTO2.setStartTime(reqClassroomDTO.getStartTime());
        reqClassroomDTO2.setEndTime(reqClassroomDTO.getEndTime());

        classRoomService.createClassroom(reqClassroomDTO2);

        model.addAttribute("msg", "Classroom added !");

        model.addAttribute("teacherList", classRoomService.fetchTeacherListForClassroom(reqClassroomDTO.getBatchId()));
        return new ModelAndView("A003-06", "reqClassroomDTO", reqClassroomDTO);
    }

    @GetMapping("/setupClassroomEdit/{classId}")
    public ModelAndView classroomEditSetup(Model model, @PathVariable("classId") Integer id) {
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setId(id);
        Classroom classroom = classRoomService.fetchClassroom(classroomDTO);

        ReqClassroomDTO reqClassroomDTO = new ReqClassroomDTO();
        reqClassroomDTO.setBatchId(classroom.getBatch().getId());
        reqClassroomDTO.setDate(classroom.getDate());
        reqClassroomDTO.setLink(classroom.getLink());
        reqClassroomDTO.setTeacherName(classroom.getTeacherName());
        reqClassroomDTO.setStartTime(classroom.getStartTime());
        reqClassroomDTO.setEndTime(classroom.getEndTime());

        model.addAttribute("teacherList", classRoomService.fetchTeacherListForClassroom(classroom.getBatch().getId()));
        return new ModelAndView("A003-07","reqClassroom",reqClassroomDTO);
    }

    @PostMapping("/editClassroom")
    public String classroomEdit(){
        return "";
    }
}
