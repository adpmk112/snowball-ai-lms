package com.ace.ai.student.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.student.dtomodel.ChapterFileDTO;
import com.ace.ai.student.dtomodel.StuCommentPostDTO;
import com.ace.ai.student.dtomodel.StuReplyPostDTO;
import com.ace.ai.student.service.StudentChapterService;
import com.ace.ai.student.service.StudentCommentService;


//need to add comments
@Controller
@RequestMapping("/student")
public class StudentChapterController {
    @Autowired
    StudentChapterService studentChapterService;
    @Autowired
    StudentCommentService studentCommentService;
    

    @GetMapping("/chapter")
    public ModelAndView getChapterFileList(@RequestParam("chapterId") int chapterId,@RequestParam("batchId") int batchId,ModelMap model){
        List<ChapterFileDTO> chapterFileDTOList = studentChapterService.getChapterFileListByChapterId(chapterId);
        model.addAttribute("chapterType", "chapter");
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", new StuCommentPostDTO());
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(batchId, "chapter"));
        return new ModelAndView("S001-01","chapterFileDTOList",chapterFileDTOList);
    }

    @GetMapping("/customChapter")
    public ModelAndView getCustomChapterFileList(@RequestParam("customChapterId") int customChapterId,@RequestParam("batchId") int batchId,ModelMap model){
        List<ChapterFileDTO> chapterFileDTOList = studentChapterService.getCustomChapterFileListByCustomChapterId(customChapterId);
        model.addAttribute("chapterType", "customChapter");
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", new StuCommentPostDTO());
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(batchId, "customChapter"));
        return new ModelAndView("S001-01","chapterFileDTOList",chapterFileDTOList);
    }

    @GetMapping("/chapter/chapterFile")
    public ModelAndView getChapterFile(@RequestParam("chapterId") int chapterId,@RequestParam("batchId") int batchId,@RequestParam("chapterFileId") int chapterFileId,ModelMap model){
        ChapterFileDTO chapterFileDTO = studentChapterService.getChapterFileById(chapterFileId, chapterId);
        model.addAttribute("chapterType", "chapter");
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", new StuCommentPostDTO());
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(batchId, "chapter"));
        return new ModelAndView("S001-02","chapterFileDTO",chapterFileDTO);
    }

    @GetMapping("/customChapter/customChapterFile")
    public ModelAndView getCustomChapterFile(@RequestParam("customChapterId") int customChapterId,@RequestParam("batchId") int batchId,@RequestParam("customChapterFileId") int customChapterFileId,ModelMap model){
        ChapterFileDTO chapterFileDTO = studentChapterService.getCustomChapterFileById(customChapterFileId, customChapterId);
        model.addAttribute("chapterType", "customChapter");
        model.addAttribute("stuReplyPostDTO",new StuReplyPostDTO());
        model.addAttribute("stuCommentPostDTO", new StuCommentPostDTO());
        model.addAttribute("stuCommentViewDTOList",studentCommentService.getCommentListByBatchIdAndLocation(batchId, "customChapter"));
        return new ModelAndView("S001-02","chapterFileDTO",chapterFileDTO);
    }

}
