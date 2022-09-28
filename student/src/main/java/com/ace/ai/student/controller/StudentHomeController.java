package com.ace.ai.student.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.ace.ai.student.config.StudentUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.Chapter;
import com.ace.ai.student.datamodel.ChapterBatch;
import com.ace.ai.student.datamodel.Comment;
import com.ace.ai.student.datamodel.CustomChapter;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.dtomodel.ChapterBatchDTO;
import com.ace.ai.student.dtomodel.StuChapterDTO;
import com.ace.ai.student.dtomodel.StuCommentPostDTO;
import com.ace.ai.student.dtomodel.StuCustomChapterDTO;
import com.ace.ai.student.dtomodel.StuReplyPostDTO;
import com.ace.ai.student.dtomodel.StuReplyViewDTO;
import com.ace.ai.student.service.StudentCommentService;
import com.ace.ai.student.service.StudentCourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value = "/student")
public class StudentHomeController {
    @Autowired
    StudentCourseService studentCourseService;
    @Autowired
    StudentCommentService studentCommentService;

    @GetMapping(value="/home")
    public ModelAndView getStudentHome(@AuthenticationPrincipal StudentUserDetails userDetails, ModelMap model) {
        Student student = studentCourseService.getStudentById(userDetails.getId());
        String batchName = student.getBatch().getName();
        String courseName = student.getBatch().getCourse().getName();
        List<ChapterBatchDTO> chapterList = studentCourseService.getChapterList(student.getBatch().getId());
        List<CustomChapter> customChapterList = studentCourseService.getCustomChapterList(student.getBatch().getId());

        List<StuChapterDTO> upCommingChapterList = new ArrayList<>();
        List<StuChapterDTO> doneChapterList = new ArrayList<>();
        List<StuChapterDTO> inProgressChapterList = new ArrayList<>();
        List<StuCustomChapterDTO> upCommingCustomChapterList = new ArrayList<>();
        List<StuCustomChapterDTO> doneCustomChapterList = new ArrayList<>();
        List<StuCustomChapterDTO> inProgressCustomChapterList = new ArrayList<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");


        for(ChapterBatchDTO chapter : chapterList){
            
            StuChapterDTO stuChapterDTO = new StuChapterDTO();
            if(chapter.getStartDate() != null && !chapter.getStartDate().isBlank() && chapter.getEndDate() != null && !chapter.getEndDate().isBlank()){
                
                LocalDate startDate = LocalDate.parse(chapter.getStartDate(),df);
                
                
                LocalDate endDate = LocalDate.parse(chapter.getEndDate(),df);
                
                
                
                    if((startDate.isEqual(LocalDate.now())||startDate.isBefore(LocalDate.now()))&&endDate.isAfter(LocalDate.now())){
                        stuChapterDTO.setId(chapter.getChapterId());
                        stuChapterDTO.setName(chapter.getName());
                        stuChapterDTO.setStatus("inProgress");
                        stuChapterDTO.setStartDate(chapter.getStartDate());
                        stuChapterDTO.setEndDate(chapter.getEndDate());
                        inProgressChapterList.add(stuChapterDTO);
                    }else if(endDate.isEqual(LocalDate.now())||endDate.isBefore(LocalDate.now())){
                        stuChapterDTO.setId(chapter.getChapterId());
                        stuChapterDTO.setName(chapter.getName());
                        stuChapterDTO.setStatus("done");
                        stuChapterDTO.setStartDate(chapter.getStartDate());
                        stuChapterDTO.setEndDate(chapter.getEndDate());
                        doneChapterList.add(stuChapterDTO);
                    }
            else{
                    stuChapterDTO.setId(chapter.getChapterId());
                    stuChapterDTO.setName(chapter.getName());
                    stuChapterDTO.setStatus("upComming");
                    stuChapterDTO.setStartDate(chapter.getStartDate());
                    stuChapterDTO.setEndDate(chapter.getEndDate());
                    upCommingChapterList.add(stuChapterDTO);
                }
            }
        }
        for(CustomChapter customChapter : customChapterList){
            StuCustomChapterDTO stuCustomChapterDTO = new StuCustomChapterDTO();
            if(customChapter.getStartDate() != null && !customChapter.getStartDate().isBlank() && customChapter.getEndDate() != null && !customChapter.getEndDate().isBlank()){
                
                LocalDate startDate = LocalDate.parse(customChapter.getStartDate(),df);
                LocalDate endDate = LocalDate.parse(customChapter.getEndDate(),df);
    
                
                    if((startDate.isEqual(LocalDate.now())||startDate.isBefore(LocalDate.now()))&&endDate.isAfter(LocalDate.now())){
                        stuCustomChapterDTO.setId(customChapter.getId());
                        stuCustomChapterDTO.setName(customChapter.getName());
                        stuCustomChapterDTO.setStatus("inProgress");
                        stuCustomChapterDTO.setStartDate(customChapter.getStartDate());
                        stuCustomChapterDTO.setEndDate(customChapter.getEndDate());
                        inProgressCustomChapterList.add(stuCustomChapterDTO);
                    }else if(endDate.isEqual(LocalDate.now())||endDate.isBefore(LocalDate.now())){
                        stuCustomChapterDTO.setId(customChapter.getId());
                        stuCustomChapterDTO.setName(customChapter.getName());
                        stuCustomChapterDTO.setStatus("done");
                        stuCustomChapterDTO.setStartDate(customChapter.getStartDate());
                        stuCustomChapterDTO.setEndDate(customChapter.getEndDate());
                        doneCustomChapterList.add(stuCustomChapterDTO);
                    }
                else{
                    stuCustomChapterDTO.setId(customChapter.getId());
                    stuCustomChapterDTO.setName(customChapter.getName());
                    stuCustomChapterDTO.setStatus("upComming");
                    stuCustomChapterDTO.setStartDate(customChapter.getStartDate());
                    stuCustomChapterDTO.setEndDate(customChapter.getEndDate());
                    upCommingCustomChapterList.add(stuCustomChapterDTO);
                }
            }          

        }

        model.addAttribute("upCommingChapterList", upCommingChapterList);
        model.addAttribute("doneChapterList", doneChapterList);
        model.addAttribute("inProgressChapterList", inProgressChapterList);
        model.addAttribute("upCommingCustomChapterList", upCommingCustomChapterList);
        model.addAttribute("doneCustomChapterList", doneCustomChapterList);
        model.addAttribute("inProgressCustomChapterList", inProgressCustomChapterList);
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", new StuCommentPostDTO());
        model.addAttribute("stuCode", student.getCode());
        model.addAttribute("batchId",student.getBatch().getId());
        model.addAttribute("stuId",userDetails.getId());
        model.addAttribute("batchName",batchName);
        model.addAttribute("courseName",courseName);
        return new ModelAndView("S001","stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(student.getBatch().getId(), "home"));
    }
    
    @PostMapping(value="/home/commentpost")
    public String postCommment(@ModelAttribute("stuCommentPostDTO") StuCommentPostDTO stuCommentPostDTO,ModelMap model){
        stuCommentPostDTO.setLocation("home");
        studentCommentService.saveComment(stuCommentPostDTO);
        return "redirect:/student/home";
    }

    @PostMapping(value="/home/replypost")
    public String postReply(@ModelAttribute("stuReplyPostDTO") StuReplyPostDTO stuReplyPostDTO,ModelMap model){
        
        studentCommentService.saveReply(stuReplyPostDTO);
        return "redirect:/student/home";
    }
    
}
