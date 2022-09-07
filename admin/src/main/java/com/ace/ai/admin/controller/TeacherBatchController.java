package com.ace.ai.admin.controller;

import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.datamodel.StudentExamMark;
import com.ace.ai.admin.dtomodel.AttendanceRequestDTO;
import com.ace.ai.admin.dtomodel.ExamMarkDTO;
import com.ace.ai.admin.dtomodel.StudentAttendDTO;
import com.ace.ai.admin.dtomodel.StudentIdMarkFilePathDTO;
import com.ace.ai.admin.service.AttendanceService;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.ChapterViewService;
import com.ace.ai.admin.service.ExamScheduleService;
import com.ace.ai.admin.service.StudentExamMarkService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "/teacher/batch")
public class TeacherBatchController {
    @Autowired
    BatchService batchService;
    @Autowired
    ChapterViewService chapterViewService;

    @Autowired
    AttendanceService attendanceService;
    @Autowired
    ExamScheduleService examScheduleService;
    @Autowired
    StudentExamMarkService studentExamMarkService;

    @GetMapping({ "/addNewActivity" })
    public String addNewActivity(Model model) {

        return "T003-01";
    }

    @GetMapping({ "/" })
    public String gotoBatch(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loginUserCode = authentication.getName();
      List<Batch> batchList = batchService.findBatchesByTeacherCode(loginUserCode);
      int totalBatch = batchList.size();
       model.addAttribute("totalBatch", totalBatch);
       model.addAttribute("batchList", batchList);
        return "T002";
}

    @GetMapping({ "/batchSeeMore" })
    public String batchSeeMore(Model model, @RequestParam("batchId")Integer batchId, @RequestParam("radio") String radio) throws ParseException {
        model.addAttribute("chapterDTOList", chapterViewService.findAllChapterInChapterBatchByBatchId(batchId));
        model.addAttribute("batchId",batchId);
        model.addAttribute("batchName", batchService.getById(batchId).getName());
        model.addAttribute("courseName", batchService.getById(batchId).getCourse().getName());// for course name in view
        model.addAttribute("radio", radio);
        model.addAttribute("attendanceDTOList", attendanceService.getAllAttendanceList(batchId));// Attendance with bath id
        model.addAttribute("allStudent", attendanceService.getAllStudentByDeleteStatus(batchId));// for attendance with batch
        model.addAttribute("examScheduleList", examScheduleService.showExamScheduleTable(batchId)); //For Exam Schedule
        model.addAttribute("studentExamMarkList", studentExamMarkService.getExamMarkDTOList(batchId));//To mark exam;

        return "T003";
    }

    @GetMapping(path = "/SendData")
    @ResponseBody
    public ResponseEntity SendData(@RequestParam("chpName") String chpName, @RequestParam("startDate") String startDate,
                                   @RequestParam("endDate") String endDate, @RequestParam("batchId") Integer batchId) {
        chapterViewService.saveDatesForChapter(chpName, startDate, endDate, batchId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //For attendance
    @PostMapping("/setAttendance")
    public String saveAttendance( @RequestBody AttendanceRequestDTO attendance ){
        int batchId=attendance.getBatchId();
        int classId = attendance.getClassId();
        List<StudentAttendDTO> studentAndAttendList = attendance.getStudentAndAttendList();
        for(StudentAttendDTO studentAndAttend : studentAndAttendList){
            int studentId = studentAndAttend.getStudentId();
            String attend = studentAndAttend.getAttend();
            Attendance attendanceFromDb = attendanceService.getByStudentIdAndClassroomId(studentId, classId);
            if(attendanceFromDb != null){
                attendanceFromDb.setAttend(attend);
                attendanceService.saveAttendance(attendanceFromDb);
            }
        }
        return "redirect:/teacher/batch/batchSeeMore?batchId="+batchId+"&radio=attendance";
    }

    //For Exam schedule
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

    //For Exam Mark
    @PostMapping("/setExamMark")
    @ResponseBody
    public ResponseEntity setExamMark(@RequestBody ExamMarkDTO examMarkDTO){
        int batchId = examMarkDTO.getBatchId();
        int examId = examMarkDTO.getExamId();
        List<StudentIdMarkFilePathDTO> studentDataList = examMarkDTO.getStudentData();
        System.out.println("All Data "+ batchId+examId+studentDataList.size());
        for(StudentIdMarkFilePathDTO studentData : studentDataList){
            int studentId = studentData.getStudentId();
            int mark = studentData.getMark();
            StudentExamMark studentExamMark = studentExamMarkService.getByExamIdAndStudentId(examId, studentId);
            studentExamMark.setStudentMark(mark);
            studentExamMark.setNotification(false);
            studentExamMarkService.save(studentExamMark);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
