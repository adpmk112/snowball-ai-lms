package com.ace.ai.admin.controller;

import com.ace.ai.admin.config.AdminUserDetails;
import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.BatchExamForm;
import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterFile;
import com.ace.ai.admin.datamodel.CustomChapter;
import com.ace.ai.admin.datamodel.CustomChapterFile;
import com.ace.ai.admin.datamodel.StudentAssignmentMark;
import com.ace.ai.admin.datamodel.StudentExamMark;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.AssignmentMarkDTO;
import com.ace.ai.admin.dtomodel.AttendanceRequestDTO;
import com.ace.ai.admin.dtomodel.ExamMarkDTO;
import com.ace.ai.admin.dtomodel.StudentAttendDTO;
import com.ace.ai.admin.dtomodel.StudentIdMarkFilePathDTO;
import com.ace.ai.admin.dtomodel.TeacherAssignmentViewDTO;
import com.ace.ai.admin.dtomodel.TeacherCommentPostDTO;
import com.ace.ai.admin.dtomodel.TeacherCommentViewDTO;
import com.ace.ai.admin.dtomodel.TeacherReplyPostDTO;
import com.ace.ai.admin.service.AssignmentService;
import com.ace.ai.admin.service.AttendanceService;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.ChapterViewService;
import com.ace.ai.admin.service.BatchExamFormService;
import com.ace.ai.admin.service.ClassRoomService;
import com.ace.ai.admin.service.CustomChapterService;
import com.ace.ai.admin.service.StudentAssignmentMarkService;
import com.ace.ai.admin.service.StudentExamMarkService;
import com.ace.ai.admin.service.TeacherCommentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
    BatchExamFormService examScheduleService;
    @Autowired
    StudentExamMarkService studentExamMarkService;
    @Autowired
    StudentAssignmentMarkService studentAssignmentMarkService;
    @Autowired
    ClassRoomService classroomService;
    @Autowired
    AssignmentService assignmentService;
    @Autowired
    CustomChapterService customChapterService;
    @Autowired
    TeacherCommentService teacherCommentService;

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
        model.addAttribute("studentAssignmentMarkList", studentAssignmentMarkService.getAssignmentMarkDTOList(batchId));//To mark Assignment
        model.addAttribute("classroomList", classroomService.showClassroomTable(batchId));
        model.addAttribute("batchCustomChapterDTOList", customChapterService.getCustomChapterListByBatchId(batchId) );
        
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
        for(StudentIdMarkFilePathDTO studentData : studentDataList){
            int studentId = studentData.getStudentId();
            int mark = studentData.getMark();
            StudentExamMark studentExamMark = studentExamMarkService.getByExamIdAndStudentId(examId, studentId);
            if(studentExamMark != null){
                studentExamMark.setStudentMark(mark);
                studentExamMark.setNotification(false);
                studentExamMarkService.save(studentExamMark);
            }
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //For Assignment Mark
    @PostMapping("/setAssignmentMark")
    @ResponseBody
    public ResponseEntity setAssignmentMark(@RequestBody AssignmentMarkDTO assignmentMarkDTO){
        int batchId =  assignmentMarkDTO.getBatchId();
        int assignmentId = assignmentMarkDTO.getAssignmentId();
        List<StudentIdMarkFilePathDTO> studentDataList = assignmentMarkDTO.getStudentData();
        for(StudentIdMarkFilePathDTO studentData : studentDataList){
            int studentId = studentData.getStudentId();
            int mark = studentData.getMark();
            StudentAssignmentMark studentAssignmentMark = studentAssignmentMarkService.getByAssignmentIdAndStudentAssignmentMark(assignmentId, studentId);
            if(studentAssignmentMark != null){
                studentAssignmentMark.setStudentMark(mark);
                studentAssignmentMark.setNotification(false);
                studentAssignmentMarkService.save(studentAssignmentMark);
            }
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/comment/home")
    public ModelAndView getCommetHome(@AuthenticationPrincipal AdminUserDetails userDetails,@RequestParam("batchId")int batchId,ModelMap model){
        List<TeacherCommentViewDTO>  teacherCommentViewDTOList=teacherCommentService.getCommentListByBatchIdAndLocation(batchId, "home");
        Teacher teacher = teacherCommentService.getTeacherByCode(userDetails.getCode());
        model.addAttribute("teacherReplyPostDTO",new TeacherReplyPostDTO());
        model.addAttribute("teacherCommentPostDTO", new TeacherCommentPostDTO());
        model.addAttribute("teacherCode", teacher.getCode());
        model.addAttribute("batchId",batchId);
        model.addAttribute("teacherId",teacher.getId());
        return new ModelAndView("","teacherCommentViewDTOList",teacherCommentViewDTOList);
    }

    @GetMapping("/comment/exam")
    public ModelAndView getCommetExam(@AuthenticationPrincipal AdminUserDetails userDetails,@RequestParam("batchId")int batchId,ModelMap model){
        List<TeacherCommentViewDTO>  teacherCommentViewDTOList=teacherCommentService.getCommentListByBatchIdAndLocation(batchId, "exam");
        Teacher teacher = teacherCommentService.getTeacherByCode(userDetails.getCode());
        model.addAttribute("teacherReplyPostDTO",new TeacherReplyPostDTO());
        model.addAttribute("teacherCommentPostDTO", new TeacherCommentPostDTO());
        model.addAttribute("teacherCode", teacher.getCode());
        model.addAttribute("batchId",batchId);
        model.addAttribute("teacherId",teacher.getId());
        return new ModelAndView("","teacherCommentViewDTOList",teacherCommentViewDTOList);
    }

    @GetMapping("/comment/assignmentList/student")
    public ModelAndView getAssignmentComment(@AuthenticationPrincipal AdminUserDetails userDetails,@RequestParam("batchId")int batchId,@RequestParam("assignmentId") int assignmentId,@RequestParam("stuCode") String stuCode,ModelMap model){
        TeacherAssignmentViewDTO teacherAssignmentViewDTO = new TeacherAssignmentViewDTO();
        teacherAssignmentViewDTO.setAssignmentId(assignmentId);
        teacherAssignmentViewDTO.setBatchId(batchId);
        teacherAssignmentViewDTO.setStuCode(stuCode);
        teacherAssignmentViewDTO.setTeacherCode(teacherCommentService.getTeacherCodeListByBatchId(batchId));
        List<TeacherCommentViewDTO>  teacherCommentViewDTOList=teacherCommentService.getCommentListByBatchIdAndLocationAndCommenterCode(teacherAssignmentViewDTO);
        Teacher teacher = teacherCommentService.getTeacherByCode(userDetails.getCode());
        model.addAttribute("teacherReplyPostDTO",new TeacherReplyPostDTO());
        model.addAttribute("teacherCommentPostDTO", new TeacherCommentPostDTO());
        model.addAttribute("teacherCode", teacher.getCode());
        model.addAttribute("batchId",batchId);
        model.addAttribute("teacherId",teacher.getId());
        return new ModelAndView("","teacherCommentViewDTOList",teacherCommentViewDTOList);
    }

    @GetMapping("/comment/assignmnetList")
    public ModelAndView getAssignemntCommentList(@RequestParam("batchId") int batchId,ModelMap model){
        
        
        model.addAttribute("batchId", batchId);
        return new ModelAndView("T005","assignemntList",teacherCommentService.getAssignmentListByBatchId(batchId));
    }

    @GetMapping("/comment/chapterList")
    public ModelAndView getChpaterCommentList(@RequestParam("batchId") int batchId,ModelMap model){
        model.addAttribute("batchId", batchId);
        
        return new ModelAndView("","chapterAndCustomChapterList",teacherCommentService.getChapterListAndCustomChapterListByBatchId(batchId));
    }

    @GetMapping("/comment/chapterList/chapter")
    public ModelAndView getChapterComment(@AuthenticationPrincipal AdminUserDetails userDetails,@RequestParam("batchId")int batchId,@RequestParam("chapterId")int chapterId,ModelMap model){
        Chapter chapter = teacherCommentService.findChapterById(chapterId);
    
        List<TeacherCommentViewDTO>  teacherCommentViewDTOList=teacherCommentService.getCommentListByBatchIdAndLocation(batchId, chapter.getName());
        Teacher teacher = teacherCommentService.getTeacherByCode(userDetails.getCode());
        model.addAttribute("teacherReplyPostDTO",new TeacherReplyPostDTO());
        model.addAttribute("teacherCommentPostDTO", new TeacherCommentPostDTO());
        model.addAttribute("teacherCode", teacher.getCode());
        model.addAttribute("batchId",batchId);
        model.addAttribute("teacherId",teacher.getId());
        model.addAttribute("chapterId",chapter.getId());
        model.addAttribute("chapterFileName",chapter.getName());
        model.addAttribute("chapterType","chapter");
        return new ModelAndView("","teacherCommentViewDTOList",teacherCommentViewDTOList);
    }

    @GetMapping("/comment/chapterList/chapter")
    public ModelAndView getCustomChapterComment(@AuthenticationPrincipal AdminUserDetails userDetails,@RequestParam("batchId")int batchId,@RequestParam("chapterId")int chapterId,ModelMap model){
        CustomChapter customChapter = teacherCommentService.findCustomChapterById(chapterId);
    
        List<TeacherCommentViewDTO>  teacherCommentViewDTOList=teacherCommentService.getCommentListByBatchIdAndLocation(batchId, customChapter.getName());
        Teacher teacher = teacherCommentService.getTeacherByCode(userDetails.getCode());
        model.addAttribute("teacherReplyPostDTO",new TeacherReplyPostDTO());
        model.addAttribute("teacherCommentPostDTO", new TeacherCommentPostDTO());
        model.addAttribute("teacherCode", teacher.getCode());
        model.addAttribute("batchId",batchId);
        model.addAttribute("teacherId",teacher.getId());
        model.addAttribute("chapterId",customChapter.getId());
        model.addAttribute("chapterFileName",customChapter.getName());
        model.addAttribute("chapterType","chapter");
        return new ModelAndView("","teacherCommentViewDTOList",teacherCommentViewDTOList);
    }

    @GetMapping("/comment/videoList")
    public ModelAndView getVideoCommentList(@RequestParam("batchId") int batchId,ModelMap model){
        model.addAttribute("batchId", batchId);
        return new ModelAndView("T005-06","videoList",teacherCommentService.getVideoListByBatchId(batchId));
    }

    @GetMapping("/comment/videoList/chapterVideo")
    public ModelAndView getChapterVideoComment(@AuthenticationPrincipal AdminUserDetails userDetails,@RequestParam("batchId")int batchId,@RequestParam("videoid")int videoId,ModelMap model){
        ChapterFile chapterFile = teacherCommentService.findChapterFileById(videoId);
    
        List<TeacherCommentViewDTO>  teacherCommentViewDTOList=teacherCommentService.getCommentListByBatchIdAndLocation(batchId, chapterFile.getName());
        Teacher teacher = teacherCommentService.getTeacherByCode(userDetails.getCode());
        model.addAttribute("teacherReplyPostDTO",new TeacherReplyPostDTO());
        model.addAttribute("teacherCommentPostDTO", new TeacherCommentPostDTO());
        model.addAttribute("teacherCode", teacher.getCode());
        model.addAttribute("batchId",batchId);
        model.addAttribute("teacherId",teacher.getId());
        model.addAttribute("chapterId",chapterFile.getChapter().getId());
        model.addAttribute("chapterFileName",chapterFile.getName());
        model.addAttribute("chapterType","chapter");
        return new ModelAndView("T005-07","teacherCommentViewDTOList",teacherCommentViewDTOList);
    }

    @GetMapping("/comment/videoList/customChapterVideo")
    public ModelAndView getChaperVideoComment(@AuthenticationPrincipal AdminUserDetails userDetails,@RequestParam("batchId")int batchId,@RequestParam("videoid")int videoId,ModelMap model){
        CustomChapterFile customChapterFile = teacherCommentService.findCustomChapterFileById(videoId);
    
        List<TeacherCommentViewDTO>  teacherCommentViewDTOList=teacherCommentService.getCommentListByBatchIdAndLocation(batchId, customChapterFile.getName());
        Teacher teacher = teacherCommentService.getTeacherByCode(userDetails.getCode());
        model.addAttribute("teacherReplyPostDTO",new TeacherReplyPostDTO());
        model.addAttribute("teacherCommentPostDTO", new TeacherCommentPostDTO());
        model.addAttribute("teacherCode", teacher.getCode());
        model.addAttribute("batchId",batchId);
        model.addAttribute("teacherId",teacher.getId());
        model.addAttribute("chapterId",customChapterFile.getCustomChapter().getId());
        model.addAttribute("chapterFileName",customChapterFile.getName());
        model.addAttribute("chapterType","customChapter");
        return new ModelAndView("","teacherCommentViewDTOList",teacherCommentViewDTOList);
    }

    @PostMapping(value="/chapter/commentpost")
    public String postChapterCommment(@ModelAttribute("teacherCommentPostDTO") TeacherCommentPostDTO teacherCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        teacherCommentService.saveComment(teacherCommentPostDTO);
        return "redirect:/teacher/batch/comment/chapterList/chapter?batchId="+teacherCommentPostDTO.getBatchId()+"&chapterId="+teacherCommentPostDTO.getLocationId();
    }

    @PostMapping(value="/chapter/replypost")
    public String postChapterReply(@ModelAttribute("teacherReplyPostDTO") TeacherReplyPostDTO teacherReplyPostDTO,ModelMap model){
        
        teacherCommentService.saveReply(teacherReplyPostDTO);
        return "redirect:/teacher/batch/comment/chapterList/chapter?batchId="+teacherReplyPostDTO.getBatchId()+"&chapterId="+teacherReplyPostDTO.getLocationId();
    }
    @PostMapping(value="/customChapter/commentpost")
    public String postCustomChapterCommment(@ModelAttribute("teacherCommentPostDTO") TeacherCommentPostDTO teacherCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        teacherCommentService.saveComment(teacherCommentPostDTO);
        return "redirect:/teacher/batch/comment/customChapterList/chapter?batchId="+teacherCommentPostDTO.getBatchId()+"&chapterId="+teacherCommentPostDTO.getLocationId();
    }

    @PostMapping(value="/customChapter/replypost")
    public String postCustomChapterReply(@ModelAttribute("teacherReplyPostDTO") TeacherReplyPostDTO teacherReplyPostDTO,ModelMap model){
        
        teacherCommentService.saveReply(teacherReplyPostDTO);
        return "redirect:/teacher/batch/comment/customChapterList/chapter?batchId="+teacherReplyPostDTO.getBatchId()+"&chapterId="+teacherReplyPostDTO.getLocationId();
    }

    @PostMapping(value="/exam/commentpost")
    public String postExamCommment(@ModelAttribute("teacherCommentPostDTO") TeacherCommentPostDTO teacherCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        teacherCommentService.saveComment(teacherCommentPostDTO);
        return "redirect:/teacher/batch/comment/exam?batchId="+teacherCommentPostDTO.getBatchId();
    }

    @PostMapping(value="/exam/replypost")
    public String postExamReply(@ModelAttribute("teacherReplyPostDTO") TeacherReplyPostDTO teacherReplyPostDTO,ModelMap model){
        
        teacherCommentService.saveReply(teacherReplyPostDTO);
        return "redirect:/teacher/batch/comment/exam?batchId="+teacherReplyPostDTO.getBatchId();
    }

    @PostMapping(value="/home/commentpost")
    public String postHomeCommment(@ModelAttribute("teacherCommentPostDTO") TeacherCommentPostDTO teacherCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        teacherCommentService.saveComment(teacherCommentPostDTO);
        return "redirect:/teacher/batch/comment/home?batchId="+teacherCommentPostDTO.getBatchId();
    }

    @PostMapping(value="/home/replypost")
    public String postHomeReply(@ModelAttribute("teacherReplyPostDTO") TeacherReplyPostDTO teacherReplyPostDTO,ModelMap model){
        
        teacherCommentService.saveReply(teacherReplyPostDTO);
        return "redirect:/teacher/batch/comment/home?batchId="+teacherReplyPostDTO.getBatchId();
    }

    @PostMapping(value="/assignment/commentpost")
    public String postAssignmentCommment(@ModelAttribute("teacherCommentPostDTO") TeacherCommentPostDTO teacherCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        teacherCommentService.saveComment(teacherCommentPostDTO);
        return "redirect:/teacher/batch/comment/assignmentList/student?batchId="+teacherCommentPostDTO.getBatchId()+"&assignmentId="+teacherCommentPostDTO.getLocationId()+"&stuCode="+teacherCommentPostDTO.getStuCodeForAssignment();
    }

    @PostMapping(value="/assignment/replypost")
    public String postAssignmentReply(@ModelAttribute("teacherReplyPostDTO") TeacherReplyPostDTO teacherReplyPostDTO,ModelMap model){
        
        teacherCommentService.saveReply(teacherReplyPostDTO);
        return "redirect:/teacher/batch/comment/assignmentList/student?batchId="+teacherReplyPostDTO.getBatchId()+"&assignmentId="+teacherReplyPostDTO.getLocationId()+"&stuCode="+teacherReplyPostDTO.getStuCodeForAssignment();
    }

    @PostMapping(value="/chapterVideo/commentpost")
    public String postVideoCommment(@ModelAttribute("teacherCommentPostDTO") TeacherCommentPostDTO teacherCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        teacherCommentService.saveComment(teacherCommentPostDTO);
        return "redirect:/teacher/batch/comment/videoList/chapterVideo?batchId="+teacherCommentPostDTO.getBatchId()+"&videoid="+teacherCommentPostDTO.getLocationId();
    }

    @PostMapping(value="/customChapterVideo/replypost")
    public String postVideoReply(@ModelAttribute("teacherReplyPostDTO") TeacherReplyPostDTO teacherReplyPostDTO,ModelMap model){
        
        teacherCommentService.saveReply(teacherReplyPostDTO);
        return "redirect:/teacher/batch/comment/videoList/chapterVideo?batchId="+teacherReplyPostDTO.getBatchId()+"&videoId="+teacherReplyPostDTO.getLocationId();
    }

}
