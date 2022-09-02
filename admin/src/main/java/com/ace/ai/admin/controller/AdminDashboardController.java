package com.ace.ai.admin.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.AdminBatchAttendanceDTO;
import com.ace.ai.admin.dtomodel.StudentAttendanceDTO;
import com.ace.ai.admin.service.AdminDashboardService;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @Autowired
    AdminDashboardService adminDashboardService;

    @GetMapping("/home")
    public String adminDashboard(ModelMap model){
        List<Course> courseList = adminDashboardService .getCourseList(false);
        model.addAttribute("courseCount", courseList.size());
        List<Batch> batchList = adminDashboardService.getBatchList(false);
        model.addAttribute("batchList",batchList);
        model.addAttribute("batchCount",batchList.size());
        List<Teacher> teacherList = adminDashboardService.getTeacherList(false);
        model.addAttribute("teacherCount",teacherList.size());
    return "A001";
    }

    @GetMapping("/grap")
    @ResponseBody
    public List<StudentAttendanceDTO> grapAdd(@RequestParam("batchId") Integer batch){
           AdminBatchAttendanceDTO adminAllBatchAttendanceListDTO = new AdminBatchAttendanceDTO();
           List<StudentAttendanceDTO> studentListByBatch = adminDashboardService.getStuAttendanceByBatch(batch);
           adminAllBatchAttendanceListDTO.setAdminDashboardDTO(studentListByBatch);
           return adminAllBatchAttendanceListDTO.getAdminDashboardDTO();
    }

}
