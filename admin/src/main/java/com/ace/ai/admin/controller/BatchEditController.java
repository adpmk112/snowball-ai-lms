package com.ace.ai.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.dtomodel.BatchDTO;
import com.ace.ai.admin.dtomodel.StudentDTO;
import com.ace.ai.admin.dtomodel.TeacherDTO;
import com.ace.ai.admin.service.AttendanceService;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.ChapterViewService;
import com.ace.ai.admin.service.ExamFormService;
import com.ace.ai.admin.service.ClassRoomService;
import com.ace.ai.admin.service.ExamScheduleService;
import com.ace.ai.admin.service.TeacherBatchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BatchEditController {
    
    @Autowired
    BatchService batchService;

    @Autowired
    TeacherBatchService teacherBatchService;

    @GetMapping("/showEditBatch/{id}")
    public String editBatch(@PathVariable("id") int id , Model model){
        Batch batch = batchService.findBatchById(id);
        List<Course> courseList = batchService.findAllCourseByDeleteStatus();
        List<Teacher> teacherList = batchService.findALlTeacherByDeleteStatus(false); // All teacher
        
        BatchDTO batchDTO = new BatchDTO();
        batchDTO.setName(batch.getName());
        batchDTO.setCourseId(batch.getCourse().getId());
        batchDTO.setBatchId(batch.getId());

        List<TeacherBatch> teachersFromBatch = batch.getTeacherBatches(); //For batchteachers
        List<Integer> teacherIds =new ArrayList<Integer>();
        for(TeacherBatch teacherFromBatch: teachersFromBatch){
            teacherIds.add(teacherFromBatch.getId());
        }
        batchDTO.setTeacherId(teacherIds);

        model.addAttribute("teacherList", teacherList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("batchDTO", batchDTO);
        return "A003-02";
    }

    @PostMapping("/updateBatch")
    public String saveBatch(@ModelAttribute("batchDTO") BatchDTO batchDTO) {
        int batchId = batchDTO.getBatchId();
        System.out.println("Batch Name is "+batchDTO.getName());
        Batch batch = batchService.findBatchById(batchId);
        System.out.print("batch info"+ batchId+ batchDTO.getName()+ batch.getName());
        batch.setName(batchDTO.getName());
        //     LocalDate localDate = LocalDate.now();
        //     DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //     String localDateString = localDate.format(dateTimeFormatter);
        // batch.setCreatedDate(localDateString);//This will be updated Date
             
        batchService.saveBatch(batch);
        //Delete All Teacher
        teacherBatchService.deleteByBatchId(batchId);
        for(Integer t_id : batchDTO.getTeacherId()){
            batchService.saveTeacherBatch(t_id, batch.getId());
        }
        return "redirect:/goToBatch";
        //return "redirect:/updateBatchSuccess/"+batchId;
    }

    // @GetMapping("/updateBatchSuccess/{batchId}")
    // public String updateSuccess(Model model, @PathVariable("batchId") int batchId){
    //     BatchDTO batchDTO = new BatchDTO();
    //     batchDTO.setBatchId(batchId);
    //     model.addAttribute("", new BatchDTO());

    //     model.addAttribute("msg", "Batch is updated successfully.");
    //     return "A003-02";
    // }

}
