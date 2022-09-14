package com.ace.ai.admin.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.dtomodel.TeacherCommentDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardChartDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardChartJsDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardExamDTO;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.TeacherDashboardService;
import com.ace.ai.admin.service.TeacherService;



@Controller
@RequestMapping("/teacher")
public class TeacherDashboardController {

    @Autowired
    TeacherDashboardService teacherDashboardService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    BatchService batchService;
    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/home")
    public String teacherDashboard(ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacherCode = authentication.getName();
        System.out.println(teacherCode+"  TeacherName");
        List< Batch> batchList = teacherDashboardService.findBatchesByTeacherCode(teacherCode);
        List<TeacherDashboardDTO> batchStudentList = teacherDashboardService.getBatchNameAndStuCountByTeacherCode(teacherCode);
        int totalBatch = batchList.size();
        model.addAttribute("totalBatch", totalBatch);
        model.addAttribute("batchList", batchList);
        model.addAttribute("batchStudentList", batchStudentList);
        return "T001";
    }

    @GetMapping("/grap")
    @ResponseBody
    public List<TeacherDashboardChartDTO> getStudentAttendance(@RequestParam("batchId") int batchId){
        TeacherDashboardChartJsDTO teacherDashboardChartJsDTO = new TeacherDashboardChartJsDTO();
        List<TeacherDashboardChartDTO> teacherDashboardChartDTOList = teacherDashboardService.findStudentByBatchId(batchId);
        teacherDashboardChartJsDTO.setStudentAttendance(teacherDashboardChartDTOList);
        return teacherDashboardChartJsDTO.getStudentAttendance();
    }


    @GetMapping("/home/markAndComment")
    public String markAndComment(@RequestParam("batchId") int batchId,ModelMap model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String teacherCode = authentication.getName();
        System.out.println(teacherCode+"  TeacherName");
        List< Batch> batchList = teacherDashboardService.findBatchesByTeacherCode(teacherCode);
        List<TeacherDashboardDTO> batchStudentList = teacherDashboardService.getBatchNameAndStuCountByTeacherCode(teacherCode);
        int totalBatch = batchList.size();
        List<TeacherDashboardExamDTO> teacherDashboardExamDTO = teacherDashboardService.getStudentNameAndExamMarkByBatchId(batchId);
        List<Student> studentNameList = studentRepository.findByDeleteStatusAndBatchId(false,batchId);
        List<TeacherCommentDTO> teacherCommentDTOList = teacherDashboardService.getCommentByBatchId(batchId);
        model.addAttribute("teacherDashboardExamDTO", teacherDashboardExamDTO);
        model.addAttribute("totalBatch", totalBatch);
        model.addAttribute("batchList", batchList);
        model.addAttribute("batchStudentList", batchStudentList);
        model.addAttribute("studentNameList", studentNameList);
        model.addAttribute("teacherCommentDTOList", teacherCommentDTOList);
        return "T001";
    }

}
