package com.ace.ai.admin.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.AdminBatchAttendanceDTO;
import com.ace.ai.admin.dtomodel.StudentAttendanceDTO;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.CourseRepository;
import com.ace.ai.admin.repository.TeacherRepository;
import com.ace.ai.admin.service.AdminDashboardService;
import com.ace.ai.admin.service.AttendanceService;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    BatchRepository batchRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    AdminDashboardService adminDashboardService;

    @GetMapping("/home")
    public ModelAndView adminDashboard(ModelMap model){
        List<Course> courseList=courseRepository.findByDeleteStatus(false);
        int courseCount = courseList.size();
        model.addAttribute("courseCount", courseCount);
        List<Batch> batchList=batchRepository.findByDeleteStatus(false);
        int batchCount = batchList.size();
        model.addAttribute("batchCount",batchCount);
        List<Teacher> teacherList=teacherRepository.findByDeleteStatus(false);
        int teacherCount = teacherList.size();
        model.addAttribute("teacherCount",teacherCount);
        //this is all batch list with each student's attendance info
        List<AdminBatchAttendanceDTO> allBatchAttendanceList = new ArrayList<>();
        for(Batch batch : batchList){
            AdminBatchAttendanceDTO adminAllBatchAttendanceListDTO = new AdminBatchAttendanceDTO();
            //get students' attend list by batch
            List<StudentAttendanceDTO> studentListByBatch = adminDashboardService.getStuAttendanceByBatch(batch.getId());
            adminAllBatchAttendanceListDTO.setAdminDashboardDTO(studentListByBatch);
            adminAllBatchAttendanceListDTO.setBatchId(batch.getId());
            //add one batch student list to all batch list
            allBatchAttendanceList.add(adminAllBatchAttendanceListDTO);
        }
        
        return new ModelAndView("test","batchList",allBatchAttendanceList);
    }
}
