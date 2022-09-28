package com.ace.ai.admin.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.dtomodel.ClassroomDTO;
import com.ace.ai.admin.dtomodel.ReqClassroomDTO;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.ClassRoomService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller

@RequestMapping(value = "/admin/batch")
public class ClassRoomController {
    @Autowired
    ClassRoomService classRoomService;

    @Autowired
    BatchService batchService;

    @GetMapping("/setupClassroomAdd/{batch_id}")
    public ModelAndView classroomAdd(Model model, @PathVariable("batch_id") Integer id) {
        ReqClassroomDTO reqClassroomDTO = new ReqClassroomDTO();
        reqClassroomDTO.setBatchId(id);
        model.addAttribute("teacherList", classRoomService.fetchTeacherListForClassroom(id));
        model.addAttribute("batchId", id);
        model.addAttribute("batchName", batchService.getById(id).getName());
        model.addAttribute("courseName", batchService.getById(id).getCourse().getName());
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
        int batchId = reqClassroomDTO.getBatchId();
        model.addAttribute("success", "Classroom added !");
        model.addAttribute("batchId", reqClassroomDTO.getBatchId());
        model.addAttribute("batchName", batchService.getById(batchId).getName());
        model.addAttribute("courseName", batchService.getById(batchId).getCourse().getName());
        model.addAttribute("teacherList", classRoomService.fetchTeacherListForClassroom(reqClassroomDTO.getBatchId()));
        ReqClassroomDTO newEmpty = new ReqClassroomDTO();
        newEmpty.setBatchId(reqClassroomDTO.getBatchId());
        newEmpty.setTeacherName(reqClassroomDTO.getTeacherName());
        return new ModelAndView("A003-06", "reqClassroomDTO", newEmpty);
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

        model.addAttribute("classId", classroomDTO.getId());
        model.addAttribute("teacherList", classRoomService.fetchTeacherListForClassroom(classroom.getBatch().getId()));
        model.addAttribute("batchName", classroom.getBatch().getName());
        model.addAttribute("batchId", classroom.getBatch().getId());
        model.addAttribute("courseName", classroom.getBatch().getCourse().getName());
        return new ModelAndView("A003-07","reqClassroom",reqClassroomDTO);
    }

    @PostMapping("/editClassroom/{classId}")
    public String classroomEdit(Model model,@PathVariable("classId") Integer id,
        @ModelAttribute("reqClassroom")ReqClassroomDTO reqClassroomDTO) throws ParseException{

        ClassroomDTO classroomDTO1 = new ClassroomDTO();
        classroomDTO1.setId(id);
        Classroom classroom = classRoomService.fetchClassroom(classroomDTO1);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ClassroomDTO classroomDTO = new ClassroomDTO();
        classroomDTO.setId(id);
        classroomDTO.setDate(LocalDate.parse(reqClassroomDTO.getDate(),df));
        classroomDTO.setLink(reqClassroomDTO.getLink());
        classroomDTO.setTeacherName(reqClassroomDTO.getTeacherName());
        classroomDTO.setStartTime(reqClassroomDTO.getStartTime());
        classroomDTO.setEndTime(reqClassroomDTO.getEndTime());
        classroomDTO.setBatchId(classroom.getBatch().getId());
        
        classRoomService.editClassroom(classroomDTO);
        model.addAttribute("batchName", classroom.getBatch().getName());
        model.addAttribute("batchId", classroom.getBatch().getId());
        model.addAttribute("courseName", classroom.getBatch().getCourse().getName());
        model.addAttribute("success", "Classroom updated !");
        return "A003-07";
    }

    @GetMapping("/deleteClassroom/{classId}")
    public String classroomDelete(Model model,@PathVariable("classId") Integer id) throws ParseException {
    	
    	 ClassroomDTO classroomDTO1 = new ClassroomDTO();
         classroomDTO1.setId(id);
         Classroom classroom = classRoomService.fetchClassroom(classroomDTO1);

         DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         ClassroomDTO classroomDTO = new ClassroomDTO();
         classroomDTO.setId(id);
         classroomDTO.setDate(LocalDate.parse(classroom.getDate(),df));
         classroomDTO.setLink(classroom.getLink());
         classroomDTO.setTeacherName(classroom.getTeacherName());
         classroomDTO.setStartTime(classroom.getStartTime());
         classroomDTO.setEndTime(classroom.getEndTime());
         classroomDTO.setBatchId(classroom.getBatch().getId());
         
         classRoomService.deleteClassroom(classroomDTO);;
         model.addAttribute("batchName", classroom.getBatch().getName());
         model.addAttribute("batchId", classroom.getBatch().getId());
         model.addAttribute("courseName", classroom.getBatch().getCourse().getName());
         return "redirect:/admin/batch/batchSeeMore?id=" +classroom.getBatch().getId()+ "&radio=classroom";

    }
}


