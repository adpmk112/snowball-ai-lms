package com.ace.ai.student.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.student.config.StudentUserDetails;
import com.ace.ai.student.datamodel.Classroom;
import com.ace.ai.student.datamodel.RecordVideo;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.dtomodel.ClassroomDTO;
import com.ace.ai.student.dtomodel.RecordVideoDTO;
import com.ace.ai.student.dtomodel.RecordVideoEditDTO;
import com.ace.ai.student.service.ClassroomService;
import com.ace.ai.student.service.RecordVideoService;
import com.ace.ai.student.service.StudentCourseService;

@Controller
@RequestMapping("/student")
public class StudentClassroomController {
    
    @Autowired
    ClassroomService classroomService;

    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    RecordVideoService recordVideoService;

    @GetMapping("/livesession")
    public String setupLiveSession(ModelMap model, @AuthenticationPrincipal StudentUserDetails userDetails) throws ParseException{
        
        Student student = studentCourseService.getStudentById(userDetails.getId());
        
        List<ClassroomDTO> upcomingClassroomList = classroomService.comingClassroom(student.getBatch().getId());
        
        List<ClassroomDTO> previousClassroomList = classroomService.previousClassroom(student.getBatch().getId(), student.getId());

        model.addAttribute("upcomingClass", upcomingClassroomList);
        model.addAttribute("previousClass", previousClassroomList);
        model.addAttribute("batchId", student.getBatch().getId());

        return "S002";
    }

    // @GetMapping(value = "/recordVideo")
    // public ModelAndView getRecordVideo(@RequestParam("classroomId") int classroomId, ModelMap model) {
    //     List<RecordVideo> recordVideoList = recordVideoService.getRecordVideoByClassroomId(classroomId);

    //     model.addAttribute("classroomId", classroomId);
    //     model.addAttribute("recordVideoList", recordVideoList);
    //     model.addAttribute("recordVideoEditDTO", new RecordVideoEditDTO());
    //     return new ModelAndView("A003-08", "recordVideoDTO", new RecordVideoDTO());
    // }
}
