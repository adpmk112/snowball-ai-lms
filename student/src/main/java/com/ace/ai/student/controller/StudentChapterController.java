package com.ace.ai.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.student.config.StudentUserDetails;
import com.ace.ai.student.datamodel.Chapter;
import com.ace.ai.student.datamodel.CustomChapter;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.dtomodel.ChapterFileDTO;
import com.ace.ai.student.dtomodel.StuCommentPostDTO;
import com.ace.ai.student.dtomodel.StuReplyPostDTO;
import com.ace.ai.student.service.StudentChapterService;
import com.ace.ai.student.service.StudentCommentService;
import com.ace.ai.student.service.StudentCourseService;


//need to add comments
@Controller
@RequestMapping("/student")
public class StudentChapterController {
    @Autowired
    StudentChapterService studentChapterService;
    @Autowired
    StudentCommentService studentCommentService;
    @Autowired
    StudentCourseService studentCourseService;

    @GetMapping("/chapter")
    public ModelAndView getChapterFileList(@AuthenticationPrincipal StudentUserDetails userDetails,@RequestParam("chapterId") int chapterId,ModelMap model){
        Chapter chapter = studentChapterService.getChapterById(chapterId);
        StuCommentPostDTO stuCommentPostDTO = new StuCommentPostDTO();
        Student student = studentCourseService.getStudentById(userDetails.getId());
        List<ChapterFileDTO> chapterFileDTOList = studentChapterService.getChapterFileListByChapterId(chapterId);
        stuCommentPostDTO.setLocation(studentChapterService.findChapterById(chapterId).getName());
        model.addAttribute("chapterType", "chapter");
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", stuCommentPostDTO);
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(student.getBatch().getId(),  studentChapterService.findChapterById(chapterId).getName()));
        model.addAttribute("batchName",studentChapterService.findBatchById(student.getBatch().getId()).getName());
        model.addAttribute("chapterName", studentChapterService.findChapterById(chapterId).getName());
        model.addAttribute("chapterId", chapterId);
        
        model.addAttribute("stuCode", student.getCode());
        model.addAttribute("batchId",student.getBatch().getId());
        model.addAttribute("stuId",userDetails.getId());
        model.addAttribute("assignmentList",studentChapterService.getAssignmentListByChapterNameAndBatchId(chapter.getName(), student.getBatch().getId()));
        return new ModelAndView("S001-01","chapterFileDTOList",chapterFileDTOList);
    }

    @GetMapping("/customChapter")
    public ModelAndView getCustomChapterFileList(@AuthenticationPrincipal StudentUserDetails userDetails,@RequestParam("customChapterId") int customChapterId,ModelMap model){
        CustomChapter customChapter = studentChapterService.getCustomChapterById(customChapterId);
        StuCommentPostDTO stuCommentPostDTO = new StuCommentPostDTO();
        Student student = studentCourseService.getStudentById(userDetails.getId());
        List<ChapterFileDTO> chapterFileDTOList = studentChapterService.getCustomChapterFileListByCustomChapterId(customChapterId);
        stuCommentPostDTO.setLocation(studentChapterService.findCustomChapterById(customChapterId).getName());
        model.addAttribute("chapterType", "customChapter");
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", stuCommentPostDTO);
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(student.getBatch().getId(), studentChapterService.findCustomChapterById(customChapterId).getName()));
        model.addAttribute("batchName",studentChapterService.findBatchById(student.getBatch().getId()).getName());
        model.addAttribute("chapterName", studentChapterService.findCustomChapterById(customChapterId).getName());
        model.addAttribute("chapterId", customChapterId);
        model.addAttribute("assignmentList",studentChapterService.getAssignmentListByChapterNameAndBatchId(customChapter.getName(), student.getBatch().getId()));
        model.addAttribute("stuCode", student.getCode());
        model.addAttribute("batchId",student.getBatch().getId());
        model.addAttribute("stuId",userDetails.getId());
        return new ModelAndView("S001-01","chapterFileDTOList",chapterFileDTOList);
    }

    @GetMapping("/chapter/video")
    public ModelAndView getChapterFile(@AuthenticationPrincipal StudentUserDetails userDetails,@RequestParam("chapterId") int chapterId,@RequestParam("chapterFileId") int chapterFileId,ModelMap model){
        ChapterFileDTO chapterFileDTO = studentChapterService.getChapterFileById(chapterFileId, chapterId);
        StuCommentPostDTO stuCommentPostDTO = new StuCommentPostDTO();
        Student student = studentCourseService.getStudentById(userDetails.getId());
        stuCommentPostDTO.setLocation(chapterFileDTO.getName());
        model.addAttribute("chapterType", "chapter");
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO",stuCommentPostDTO );
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(student.getBatch().getId(), chapterFileDTO.getName()));
        model.addAttribute("stuId",userDetails.getId());
        model.addAttribute("chapterId", chapterId);
        model.addAttribute("batchId",student.getBatch().getId());
        model.addAttribute("stuCode", student.getCode());
        return new ModelAndView("S001-02","chapterFileDTO",chapterFileDTO);
    }

    @GetMapping("/customChapter/video")
    public ModelAndView getCustomChapterFile(@AuthenticationPrincipal StudentUserDetails userDetails,@RequestParam("customChapterId") int customChapterId,@RequestParam("customChapterFileId") int customChapterFileId,ModelMap model){
        ChapterFileDTO chapterFileDTO = studentChapterService.getCustomChapterFileById(customChapterFileId, customChapterId);
        StuCommentPostDTO stuCommentPostDTO = new StuCommentPostDTO();
        Student student = studentCourseService.getStudentById(userDetails.getId());
        stuCommentPostDTO.setLocation(chapterFileDTO.getName());
        model.addAttribute("chapterType", "customChapter");
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", stuCommentPostDTO);
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(student.getBatch().getId(), chapterFileDTO.getName()));
        model.addAttribute("stuId",userDetails.getId());
        model.addAttribute("chapterId", customChapterId);
        model.addAttribute("batchId",student.getBatch().getId());
        model.addAttribute("stuCode", student.getCode());
        return new ModelAndView("S001-02","chapterFileDTO",chapterFileDTO);
    }

    @PostMapping(value="/chapter/commentpost")
    public String postCommment(@ModelAttribute("stuCommentPostDTO") StuCommentPostDTO stuCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        studentCommentService.saveComment(stuCommentPostDTO);
        return "redirect:/student/chapter?chapterId="+stuCommentPostDTO.getLocationId()+"&batchId="+stuCommentPostDTO.getBatchId();
    }

    @PostMapping(value="/chapter/replypost")
    public String postReply(@ModelAttribute("stuReplyPostDTO") StuReplyPostDTO stuReplyPostDTO,ModelMap model){
        
        studentCommentService.saveReply(stuReplyPostDTO);
        studentCommentService.getCommentById(stuReplyPostDTO.getCommentId());
        return "redirect:/student/chapter?chapterId="+stuReplyPostDTO.getLocationId()+"&batchId="+stuReplyPostDTO.getBatchId();
    }

    @PostMapping(value="/customChapter/commentpost")
    public String postCustomChapterCommment(@ModelAttribute("stuCommentPostDTO") StuCommentPostDTO stuCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        studentCommentService.saveComment(stuCommentPostDTO);
        return "redirect:/student/customChapter?chapterId="+stuCommentPostDTO.getLocationId()+"&batchId="+stuCommentPostDTO.getBatchId();
    }

    @PostMapping(value="/customChapter/replypost")
    public String postCustomChapterReply(@ModelAttribute("stuReplyPostDTO") StuReplyPostDTO stuReplyPostDTO,ModelMap model){
        
        studentCommentService.saveReply(stuReplyPostDTO);
        studentCommentService.getCommentById(stuReplyPostDTO.getCommentId());
        return "redirect:/student/customChapter?chapterId="+stuReplyPostDTO.getLocationId()+"&batchId="+stuReplyPostDTO.getBatchId();
    }

    // @GetMapping(value = "/chapter/video/")
    // public ModelAndView getChapterFileVideo(@RequestParam("video")String video,@RequestParam("chapterId")int chapterId,ModelMap model){
        
    //     return new ModelAndView();
    // }

    @PostMapping(value="/chapter/video/commentpost")
    public String postVideoCommment(@ModelAttribute("stuCommentPostDTO") StuCommentPostDTO stuCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        studentCommentService.saveComment(stuCommentPostDTO);
        return "redirect:/student/chapter/video?chapterId="+stuCommentPostDTO.getLocationId()+"&chapterFileId="+stuCommentPostDTO.getChapterFileId();
    }

    @PostMapping(value="/chapter/video/replypost")
    public String postVideoReply(@ModelAttribute("stuReplyPostDTO") StuReplyPostDTO stuReplyPostDTO,ModelMap model){
        
        studentCommentService.saveReply(stuReplyPostDTO);
        studentCommentService.getCommentById(stuReplyPostDTO.getCommentId());
        return "redirect:/student/chapter/video?chapterId="+stuReplyPostDTO.getLocationId()+"&chapterFileId="+stuReplyPostDTO.getChapterFileId();
    }

    @PostMapping(value="/customChapter/video/commentpost")
    public String postCustomChapterVideoCommment(@ModelAttribute("stuCommentPostDTO") StuCommentPostDTO stuCommentPostDTO,ModelMap model){
        // stuCommentPostDTO.setLocation("home");
        studentCommentService.saveComment(stuCommentPostDTO);
        return "redirect:/student/customChapter/video?customChapterId="+stuCommentPostDTO.getLocationId()+"&customChapterFileId="+stuCommentPostDTO.getChapterFileId();
    }

    @PostMapping(value="/customChapter/video/replypost")
    public String postCustomChapterVideoReply(@ModelAttribute("stuReplyPostDTO") StuReplyPostDTO stuReplyPostDTO,ModelMap model){
        
        studentCommentService.saveReply(stuReplyPostDTO);
        studentCommentService.getCommentById(stuReplyPostDTO.getCommentId());
        return "redirect:/student/customChapter/video?customChapterId="+stuReplyPostDTO.getLocationId()+"&customChapterFileId="+stuReplyPostDTO.getChapterFileId();
    }



}
