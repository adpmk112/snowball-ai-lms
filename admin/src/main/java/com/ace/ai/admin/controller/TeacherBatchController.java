package com.ace.ai.admin.controller;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.ChapterViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping(value = "/teacher/batch")
public class TeacherBatchController {
    @Autowired
    BatchService batchService;
    @Autowired
    ChapterViewService chapterViewService;

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
    public String batchSeeMore(Model model, @RequestParam("batchId")Integer batchId) throws ParseException {
        model.addAttribute("chapterDTOList", chapterViewService.findAllChapterInChapterBatchByBatchId(batchId));
        return "T003";
    }

}
