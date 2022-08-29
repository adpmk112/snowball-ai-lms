package com.ace.ai.admin.controller;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.BatchDTO;
import com.ace.ai.admin.dtomodel.StudentDTO;
import com.ace.ai.admin.dtomodel.TeacherDTO;
import com.ace.ai.admin.service.AttendanceService;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.ChapterViewService;
<<<<<<< HEAD
import com.ace.ai.admin.service.ExamFormService;
=======
import com.ace.ai.admin.service.ClassRoomService;
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
import com.ace.ai.admin.service.ExamScheduleService;

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
public class BatchController {
    @Autowired
    ChapterViewService chapterViewService;
    @Autowired
    BatchService batchService;
    @Autowired
    ExamScheduleService examScheduleService;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
<<<<<<< HEAD
    ExamFormService examFormService;

    @GetMapping({ "/goToBatch" })
=======
    ClassRoomService classRoomService;

    @GetMapping({"/goToBatch"})
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
    public String gotoBatch(Model model) {
        List<Batch> batchList = batchService.findAll();
        model.addAttribute("batchList", batchList);
        return "A003";
    }

<<<<<<< HEAD
    @GetMapping({ "/BatchDetail1" })
=======
    @GetMapping({"/BatchDetail1"})
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
    @ResponseBody
    public String gotoBatchFromBatchLock(Model model) {
        List<Batch> batchList = batchService.findAll();
        model.addAttribute("batchList", batchList);
        return "A003";
    }

<<<<<<< HEAD
    @GetMapping({ "/batchSeeMore" })
=======
    @GetMapping({"/batchSeeMore"})
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
    public ModelAndView batchSeeMore(@RequestParam("id") Integer id, Model model) throws ParseException {
        model.addAttribute("chapterDTOList", chapterViewService.findAllChapterInChapterBatchByBatchId(id));
        model.addAttribute("teacherList", batchService.findALlTeacherByBatchId(id));
        model.addAttribute("teacherList1", batchService.findALlTeacherForAllBatchExcept(id));
        model.addAttribute("batch_id", id);
        model.addAttribute("examScheduleList", examScheduleService.showExamScheduleTable(id));
        model.addAttribute("attendanceList", attendanceService.showAttendanceTable(id));
<<<<<<< HEAD
=======
        model.addAttribute("classroomDateList", attendanceService.getClassroomDate(id));
        model.addAttribute("classroomList", classRoomService.showClassroomTable(id));
        model.addAttribute("studentDTOList", batchService.findALlStudentByBatchId(id));
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
        return new ModelAndView("A003-03", "TeacherDTO", new TeacherDTO());
    }

    @GetMapping(path = "/SendData")
    @ResponseBody
<<<<<<< HEAD
    public ResponseEntity SendData(@RequestParam("chpName") String chpName, @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate, @RequestParam("batchId") Integer batchId) {
=======
    public ResponseEntity SendData(@RequestParam("chpName") String chpName, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("batchId") Integer batchId) {
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
        chapterViewService.saveDatesForChapter(chpName, startDate, endDate, batchId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

<<<<<<< HEAD
    @GetMapping({ "/goToAddBatch" })
=======
    @GetMapping({"/goToAddBatch"})
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
    public ModelAndView gotoAddBatch(Model model) {

        List<Course> courseList = batchService.findAllCourse();
        List<Teacher> teacherList = batchService.findALlTeacherByDeleteStatus(false);
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("courseList", courseList);
        return new ModelAndView("A003-01", "batchDTO", new BatchDTO());
    }

<<<<<<< HEAD
    @GetMapping({ "/goToAddBatchSuccess" })
=======
    @GetMapping({"/goToAddBatchSuccess"})
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
    public ModelAndView gotoAddBatchSuccess(Model model) {

        List<Course> courseList = batchService.findAllCourse();
        List<Teacher> teacherList = batchService.findALlTeacherByDeleteStatus(false);
        model.addAttribute("teacherList", teacherList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("msg", "Add Teacher Success");
        return new ModelAndView("A003-01", "batchDTO", new BatchDTO());
    }

<<<<<<< HEAD
    @GetMapping({ "/CheckBatchName" })
=======
    @GetMapping({"/CheckBatchName"})
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
    @ResponseBody
    public ResponseEntity checkBatchName(@RequestParam("batchName") String batchName) {
        Batch batch = batchService.findBatchByName(batchName);
        if (batch != null) {
            return ResponseEntity.ok(HttpStatus.OK);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

<<<<<<< HEAD
    @PostMapping({ "/addBatch" })
=======
    @PostMapping({"/addBatch"})
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
    public String saveBatch(@ModelAttribute("batchDTO") BatchDTO batchDTO) {
        Batch batch = new Batch();
        batch.setDeleteStatus(false);
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDateString = localDate.format(dateTimeFormatter);
        batch.setCreatedDate(localDateString);
        batch.setName(batchDTO.getName());
        Course course = batchService.findCourseById(batchDTO.getCourseId());
        batch.setCourse(course);
        batchService.saveBatch(batch);
        batch = batchService.findLastBatch();
        batchService.saveTeacherBatch(batchDTO.getTeacherId(), batch.getId());
        // save batchExamFormTable
        List<ExamForm> examFormList = examFormService.findByCourseId(course.getId());
        for (ExamForm examForm : examFormList) {
            BatchExamForm bef = new BatchExamForm("", "", false, batch, examForm);
            examScheduleService.saveBathExamFrom(bef);
        }
        return "redirect:/goToAddBatchSuccess";
    }

    @GetMapping({ "/BatchClose" })
    @ResponseBody
    public ResponseEntity batchClose(Model model, @RequestParam("batchId") Integer batchId) {
        Batch batch = batchService.findBatchById(batchId);
        batch.setDeleteStatus(true);
        System.out.println(batch.getName());
        batchService.saveBatch(batch);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping({ "/BatchReopen" })
    @ResponseBody
    public ResponseEntity batchReopen(Model model, @RequestParam("batchId") Integer batchId) {
        Batch batch = batchService.findBatchById(batchId);
        batch.setDeleteStatus(false);
        System.out.println(batch.getName());
        batchService.saveBatch(batch);
        return ResponseEntity.ok(HttpStatus.OK);
    }

<<<<<<< HEAD
    @GetMapping({ "/addStudent" })
    public String addStudent() {
=======

    @GetMapping({"/addStudent{batch_id}"})
    public String addStudent(@PathVariable("batch_id") Integer batchId, Model model) {
        model.addAttribute("batchId", batchId);
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
        return "A003-04";
    }

    @PostMapping("/saveStudent")
<<<<<<< HEAD
    public String saveStudent(@RequestBody ArrayList<StudentDTO> studentList) {

        for (StudentDTO student : studentList) {
            System.out.println(
                    "code s" + student.getId() + " name " + student.getName() + "password" + student.getPassword());
=======
    @ResponseBody
    public void saveStudent(@RequestBody ArrayList<StudentDTO> studentList) {

        batchService.saveStudent(studentList);
    }

    @PostMapping("/addTeacherToExistingBatch")
    public String addTeacherToBatch(@RequestParam String code, @RequestParam Integer batchId) {
        batchService.addTeacherByCodeAndBatchId(code, batchId);
        return String.format("redirect:/batchSeeMore?id=%d", batchId);
    }

    @GetMapping("/editStudent{studentdata}")
    public ModelAndView editStudent(@PathVariable("studentdata") String studentdata, Model model) {
        System.out.println("Path variable is " + studentdata);
        String[] data = studentdata.split("-");
        String code = data[0];
        String id = data[1];
        model.addAttribute("edit", "edit");
        StudentDTO studentDTO = batchService.findStudentByBatchIdAndStudentId(Integer.valueOf(id), code);
        System.out.println("Batch id in studenDTO is:" + studentDTO.getBatchId());
        return new ModelAndView("A003-08", "studentDTO", studentDTO);
    }

    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute("studentDTO") StudentDTO studentDTO) {
        batchService.updateStudent(studentDTO);
        return String.format("redirect:/batchSeeMore?id=%d", studentDTO.getBatchId());
    }


    @GetMapping({"/CheckStudentId"})
    @ResponseBody
    public ResponseEntity checkStudentId(@RequestParam("id") Integer id, @RequestParam("enterId") String code) {
        System.out.println("id is :" + id);
        List<String> studentIdList = batchService.findStudentIdsByBatchId(id);
        if (studentIdList != null) {
            for (String s : studentIdList) {
                System.out.println(s);
                if (s.equals(code)) {
                    return ResponseEntity.ok(HttpStatus.OK);
                }
            }

>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
        }
        return ResponseEntity.notFound().build();
    }

<<<<<<< HEAD
    @PostMapping("/addTeacherToExistingBatch")
    public String addTeacherToBatch(@RequestParam String code, @RequestParam Integer batchId) {
        batchService.addTeacherByCodeAndBatchId(code, batchId);
        return String.format("redirect:/batchSeeMore?id=%d", batchId);
    }

    @GetMapping("/addExamSchedule")
    @ResponseBody
    public ResponseEntity addDateExamSchedule(@RequestParam("id") int id, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate){
        BatchExamForm bef = examScheduleService.findById(id);        
        bef.setStartDate(startDate);
        bef.setEndDate(endDate);
        examScheduleService.saveBathExamFrom(bef); //Update dates 
=======

    @GetMapping({"/RemoveStudent"})
    @ResponseBody
    public ResponseEntity removeStudent(Model model, @RequestParam("batchId") Integer batchId,@RequestParam("code")String code) {
          batchService.UpdateStudentByBatchIdAndCode(batchId,code);
>>>>>>> 35bdc21ed9b2eb9d8f3352537c7b8c4a6d5e77e4
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
