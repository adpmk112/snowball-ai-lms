package com.ace.ai.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.student.datamodel.RecordVideo;
import com.ace.ai.student.dtomodel.RecordVideoDTO;
import com.ace.ai.student.dtomodel.RecordVideoEditDTO;
import com.ace.ai.student.repository.ClassroomRepository;
import com.ace.ai.student.service.RecordVideoService;

@Controller
@RequestMapping(value = "/student/classroom/recordVideo")
public class StudentRecordVideoController {
    @Autowired
    RecordVideoService recordVideoService;

    @Autowired
    ClassroomRepository classroomRepository;

    @GetMapping(value = "")
    public ModelAndView getRecordVideo(@RequestParam("classroomId") int classroomId, ModelMap model) {
        List<RecordVideo> recordVideoList = recordVideoService.getRecordVideoByClassroomId(classroomId);
        int batchId = classroomRepository.getById(classroomId).getId();
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("recordVideoList", recordVideoList);
        model.addAttribute("recordVideoEditDTO", new RecordVideoEditDTO());
        model.addAttribute("batchId",batchId);
        return new ModelAndView("S002-01", "recordVideoDTO", new RecordVideoDTO());
    }
}
