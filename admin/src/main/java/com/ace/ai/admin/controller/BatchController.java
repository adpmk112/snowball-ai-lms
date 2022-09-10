package com.ace.ai.admin.controller;

import com.ace.ai.admin.datamodel.*;
import com.ace.ai.admin.dtomodel.AttendanceRequestDTO;
import com.ace.ai.admin.dtomodel.BatchDTO;
import com.ace.ai.admin.dtomodel.StudentAttendDTO;
import com.ace.ai.admin.dtomodel.StudentDTO;
import com.ace.ai.admin.dtomodel.TeacherDTO;
import com.ace.ai.admin.repository.ChapterBatchRepository;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "/admin/batch")
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
    ExamFormService examFormService;
    @Autowired
    ClassRoomService classRoomService;
    @Autowired
    TeacherBatchService teacherBatchService;
    @Autowired
    ChapterBatchRepository chapterBatchRepository;

    @GetMapping({ "/" })
    public String gotoBatch(Model model) {
        List<Batch> batchList = batchService.findAll();
        int totalBatch = batchList.size();
        model.addAttribute("totalBatch", totalBatch);
        model.addAttribute("batchList", batchList);
        return "A003";
    }

    @GetMapping({ "/BatchDetail1" })
    @ResponseBody
    public String gotoBatchFromBatchLock(Model model) {
        List<Batch> batchList = batchService.findAll();
        int totalBatch = batchList.size();
        model.addAttribute("totalBatch", totalBatch);
        model.addAttribute("batchList", batchList);
        return "A003";
    }

    @GetMapping({ "/batchSeeMore" })
    public ModelAndView batchSeeMore(@RequestParam("id") Integer id, @RequestParam("radio") String radio, Model model)
            throws ParseException {
        model.addAttribute("chapterDTOList", chapterViewService.findAllChapterInChapterBatchByBatchId(id));
        model.addAttribute("teacherList", batchService.findALlTeacherByBatchId(id));
        model.addAttribute("teacherList1", batchService.findALlTeacherForAllBatchExcept(id));
        model.addAttribute("batch_id", id);
        model.addAttribute("batchName", batchService.getById(id).getName());
        model.addAttribute("courseName", batchService.getById(id).getCourse().getName());// for course name in view
        model.addAttribute("examScheduleList", examScheduleService.showExamScheduleTable(id));
        model.addAttribute("attendanceDTOList", attendanceService.getAllAttendanceList(id));// Attendance with bath id
        model.addAttribute("allStudent", attendanceService.getAllStudentByDeleteStatus(id));// for attendance with batch
        model.addAttribute("classroomList", classRoomService.showClassroomTable(id));
        model.addAttribute("studentDTOList", batchService.findALlStudentByBatchId(id));
        model.addAttribute("radio",radio);
        return new ModelAndView("A003-03", "TeacherDTO", new TeacherDTO());
    }

    @GetMapping(path = "/SendData")
    @ResponseBody
    public ResponseEntity SendData(@RequestParam("chpName") String chpName, @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate, @RequestParam("batchId") Integer batchId) {
        chapterViewService.saveDatesForChapter(chpName, startDate, endDate, batchId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping({ "/goToAddBatch" })
    public ModelAndView gotoAddBatch(Model model) {

        List<Course> courseList = batchService.findAllCourseByDeleteStatus();
        List<Teacher> teacherList = batchService.findALlTeacherByDeleteStatus(false);
        model.addAttribute("teacherListAll", teacherList);
        model.addAttribute("courseList", courseList);
        return new ModelAndView("A003-01", "batchDTO", new BatchDTO());
    }

    @GetMapping({ "/goToAddBatchSuccess" })
    public ModelAndView gotoAddBatchSuccess(Model model) {

        List<Course> courseList = batchService.findAllCourseByDeleteStatus();
        List<Teacher> teacherList = batchService.findALlTeacherByDeleteStatus(false);
        model.addAttribute("teacherListAll", teacherList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("msg", "Batch is added successfully.");
        return new ModelAndView("A003-01", "batchDTO", new BatchDTO());
    }

    @GetMapping({ "/CheckBatchName" })
    @ResponseBody
    public ResponseEntity checkBatchName(@RequestParam("batchName") String batchName) {
        Batch batch = batchService.findBatchByName(batchName);
        if (batch != null) {
            return ResponseEntity.ok(HttpStatus.OK);
        } else {

            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping({ "/addBatch" })
    public String saveBatch(@ModelAttribute("batchDTO")@Validated BatchDTO batchDTO, BindingResult bs,Model model) {
        if(bs.hasErrors()){
            model.addAttribute("msg","Fill all Details!");
            return "redirect:/goToAddBatch";
        }else {
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
            //Find chapters for a particular course
            List<Chapter> chapters = batchService.findChapterByCourseId(batchDTO.getCourseId());
            for (Chapter chapter : chapters) {
                ChapterBatch chapterBatch = new ChapterBatch();
                chapterBatch.setChapter(chapter);
                chapterBatch.setBatch(batch);
                chapterBatch.setDeleteStatus(0);
                chapterBatchRepository.save(chapterBatch);
            }
            for (Teacher teacher : batchDTO.getTeacherList()) {
                batchService.saveTeacherBatch(teacher.getId(), batch.getId());
            }
            // save batchExamFormTable
            List<ExamForm> examFormList = examFormService.findByDeleteStatusAndCourseId(false, course.getId());
            for (ExamForm examForm : examFormList) {
                BatchExamForm bef = new BatchExamForm("", "", false, batch, examForm);
                examScheduleService.saveBathExamFrom(bef);
            }
            return "redirect:/admin/batch/goToAddBatchSuccess";
        }
    }

    @GetMapping({ "/BatchClose" })
    @ResponseBody
    public ResponseEntity batchClose(Model model, @RequestParam("batchId") Integer batchId) {
        Batch batch = batchService.findBatchById(batchId);
        batch.setDeleteStatus(true);
        batchService.saveBatch(batch);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping({ "/BatchReopen" })
    @ResponseBody
    public ResponseEntity batchReopen(Model model, @RequestParam("batchId") Integer batchId) {
        Batch batch = batchService.findBatchById(batchId);
        batch.setDeleteStatus(false);
        batchService.saveBatch(batch);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping({ "/addStudent{batch_id}" })
    public String addStudent(@PathVariable("batch_id") Integer batchId, Model model) {
        model.addAttribute("batchId", batchId);
        model.addAttribute("batchName", batchService.getById(batchId).getName());
        return "A003-04";
    }

    @PostMapping("saveStudent")
    @ResponseBody
    public ResponseEntity saveStudent(@RequestBody ArrayList<StudentDTO> studentList) {
        batchService.saveStudent(studentList);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/editStudent")
    public ModelAndView editStudent(@RequestParam("studentdata") Integer studentId, Model model,
            @RequestParam("batchId") int batchId) {
        model.addAttribute("edit", "edit");
        StudentDTO studentDTO = batchService.findStudentById(studentId);
        System.out.println("Student id in student data is:"+studentDTO.getId());
        model.addAttribute("batchName", batchService.getById(batchId).getName());
        return new ModelAndView("A003-05", "studentDTO", studentDTO);
    }

    @PostMapping("/updateStudent")
    public String updateStudent(@ModelAttribute("studentDTO") StudentDTO studentDTO) {
        System.out.println("id is"+studentDTO.getId());
       batchService.updateStudent(studentDTO);
        return "redirect:/admin/batch/batchSeeMore?id=" + studentDTO.getBatchId() + "&radio=student";
    }

    @GetMapping({ "/CheckStudentId" })
    @ResponseBody
    public ResponseEntity checkStudentId(@RequestParam("id") Integer id, @RequestParam("enterId") String code) {
        String codeChanged = code.toLowerCase();
        List<String> studentIdList = batchService.findStudentIdsByBatchId(id);
        if (studentIdList != null) {
            for (String s : studentIdList) {
                String codeTemp = s.toLowerCase();
                if (codeTemp.equals(codeChanged)) {

                    return ResponseEntity.ok(HttpStatus.OK);
                }

            }
            return ResponseEntity.notFound().build();

        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/addTeacherToBatch")
    public String addTeacherToBatch(@RequestParam("bId") Integer bId, @RequestParam("tId") Integer tId) {
        batchService.addTeacherByCodeAndBatchId(tId, bId);
        return "redirect:/admin/batch/batchSeeMore?id=" +bId+ "&radio=teacher";
    }

    @GetMapping("/addExamSchedule")
    @ResponseBody
    public ResponseEntity addDateExamSchedule(@RequestParam("id") int id, @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        BatchExamForm bef = examScheduleService.findById(id);
        bef.setStartDate(startDate);
        bef.setEndDate(endDate);
        examScheduleService.saveBathExamFrom(bef); // Update dates
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping({ "/RemoveStudent" })
    @ResponseBody
    public ResponseEntity removeStudent(Model model, @RequestParam("batchId") Integer batchId,
            @RequestParam("studentId") Integer studentId) {
        batchService.removeStudentFromBatch(studentId);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @GetMapping({ "/RemoveTeacher" })
    @ResponseBody
    public ResponseEntity removeTeacher(Model model, @RequestParam String teacherCode, @RequestParam Integer batch_id) {

        batchService.removeTeacherFromBatch(teacherCode, batch_id);
        return ResponseEntity.ok(HttpStatus.OK);

    }

    // Remove Exam Schedule
    @GetMapping("/removeExamSchedule/{id}")
    public String removeExamSchedule(@PathVariable("id") int id) {
        // update delete status
        BatchExamForm bef = examScheduleService.findById(id);
        bef.setDeleteStatus(true);
        examScheduleService.saveBathExamFrom(bef);
        int batch_id = bef.getBatch().getId();
        return "redirect:/admin/batch/batchSeeMore?id=" + batch_id + "&radio=examSchedule";
    }

    @GetMapping("/showEditBatch/{id}")
    public String editBatch(@PathVariable("id") int id, Model model) {
        Batch batch = batchService.findBatchById(id);
        List<Course> courseList = batchService.findAllCourseByDeleteStatus();
        List<Teacher> teacherList = batchService.findALlTeacherByDeleteStatus(false); // All teacher

        BatchDTO batchDTO = new BatchDTO();
        batchDTO.setName(batch.getName());
        batchDTO.setCourseId(batch.getCourse().getId());
        batchDTO.setBatchId(batch.getId());

        List<TeacherBatch> teachersFromBatch = batch.getTeacherBatches(); // For batchteachers
        List<Teacher> selectedTeacher = new ArrayList<Teacher>();
        for (TeacherBatch teacherFromBatch : teachersFromBatch) {
            Teacher teacher = teacherFromBatch.getTeacher();
            selectedTeacher.add(teacher);
        }
        batchDTO.setTeacherList(selectedTeacher);

        model.addAttribute("teacherListAll", teacherList);
        model.addAttribute("courseList", courseList);
        model.addAttribute("batchDTO", batchDTO);
        return "A003-02";
    }

    @PostMapping("/updateBatch")
    public String updateBatch(@ModelAttribute("batchDTO") BatchDTO batchDTO) {
        int batchId = batchDTO.getBatchId();
        System.out.println("Batch Name is " + batchDTO.getName());
        Batch batch = batchService.findBatchById(batchId);
        System.out.print("batch info" + batchId + batchDTO.getName() + batch.getName());
        batch.setName(batchDTO.getName());
        // LocalDate localDate = LocalDate.now();
        // DateTimeFormatter dateTimeFormatter =
        // DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // String localDateString = localDate.format(dateTimeFormatter);
        // batch.setCreatedDate(localDateString);//This will be updated Date

        batchService.saveBatch(batch);
        // Delete All Teacher
        teacherBatchService.deleteByBatchId(batchId);
        for (Teacher teacher : batchDTO.getTeacherList()) {
            batchService.saveTeacherBatch(teacher.getId(), batch.getId());
        }
        return "redirect:/admin/batch/goToBatch";
        // return "redirect:/updateBatchSuccess/"+batchId;
    }

    @PostMapping("/setAttendance")
    public String saveAttendance( @RequestBody AttendanceRequestDTO attendance ){
        int batchId=attendance.getBatchId();
        int classId = attendance.getClassId();
        List<StudentAttendDTO> studentAndAttendList = attendance.getStudentAndAttendList();
        System.out.println("studentAndAttendList is "+studentAndAttendList.size());
        for(StudentAttendDTO studentAndAttend : studentAndAttendList){
            int studentId = studentAndAttend.getStudentId();
            String attend = studentAndAttend.getAttend();
            Attendance attendanceFromDb = attendanceService.getByStudentIdAndClassroomId(studentId, classId);
            if(attendanceFromDb != null){
                attendanceFromDb.setAttend(attend);
                attendanceService.saveAttendance(attendanceFromDb);
            }
        }
        return "redirect:/admin/batch/batchSeeMore?id="+batchId+"&radio=attendance";
    }

}
