package com.ace.ai.student.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ace.ai.student.config.StudentUserDetails;
import com.ace.ai.student.dtomodel.ExamDTO;
import com.ace.ai.student.dtomodel.StuCommentPostDTO;
import com.ace.ai.student.dtomodel.StuReplyPostDTO;
import com.ace.ai.student.dtomodel.StudentExamDTO;
import com.ace.ai.student.repository.StudentRepository;
import com.ace.ai.student.service.BatchExamFormService;
import com.ace.ai.student.service.ExamFormService;
import com.ace.ai.student.service.StudentCommentService;

@Controller
@RequestMapping("/student")
public class StudentExamController {
    @Autowired BatchExamFormService batchExamFormService;
    @Autowired StudentRepository studentRepository;
    @Autowired ExamFormService examFormService;
    @Autowired
    StudentCommentService studentCommentService;
     

    @GetMapping("/exam")
    public String showExamPage(Model model,@AuthenticationPrincipal StudentUserDetails userDetails){
        int studentId =userDetails.getId();
        int batchId = studentRepository.getById(studentId).getBatch().getId();
        List<StudentExamDTO> upcomingExamList = batchExamFormService.getUpcomingExamList(batchId, studentId);
        List<StudentExamDTO> finishedExamList = batchExamFormService.getFinishedExamList(batchId, studentId);
        model.addAttribute("upcomingExamList", upcomingExamList);
        model.addAttribute("finishedExamList",finishedExamList );
        model.addAttribute("studentId", studentId);
        model.addAttribute("batchId", batchId);
        model.addAttribute("stuCode", userDetails.getCode());
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", new StuCommentPostDTO());
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(batchId, "exam"));
        return "S003";
    }

    @GetMapping("/showExamForm")
    public String showExamForm(Model model, @RequestParam("studentId") int studentId, @RequestParam("examId") int examId){
        ExamDTO exam = examFormService.getExamDTO(examId, studentId);        
        model.addAttribute("exam", exam);
        return "S003-01";
    }

    @PostMapping("/studentExamAnswer")
    public String studentExamAnswer(@ModelAttribute("exam") ExamDTO examDTO) throws IOException{
        int studentId = examDTO.getStudentId();
        String type = examDTO.getType();
        if(type.equalsIgnoreCase("multiple choice")){
            examFormService.saveAnswerAsMultipleChoice(examDTO);
        }
        else{
            examFormService.saveAnswerAsFileUpload(examDTO);
        }
        return "redirect:/student/exam";
    }

    @PostMapping(value="/exam/commentpost")
    public String postCommment(@ModelAttribute("stuCommentPostDTO") StuCommentPostDTO stuCommentPostDTO,ModelMap model){
        
        studentCommentService.saveComment(stuCommentPostDTO);
        return "redirect:/student/exam";
    }

    @PostMapping(value="/exam/replypost")
    public String postReply(@ModelAttribute("stuReplyPostDTO") StuReplyPostDTO stuReplyPostDTO,ModelMap model){
        
        studentCommentService.saveReply(stuReplyPostDTO);
        return "redirect:/student/exam";
    }
}
