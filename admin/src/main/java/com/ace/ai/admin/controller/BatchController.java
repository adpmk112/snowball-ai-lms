package com.ace.ai.admin.controller;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.dtomodel.ChapterDTO;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.ChapterViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;

@Controller
public class BatchController {
    @Autowired
    ChapterViewService chapterViewService;
    @Autowired
    BatchService batchService;

    @GetMapping({"/A003"})
    public String gotoBatch(Model model){
        List<Batch> batchList= batchService.findAllBatch();
        model.addAttribute("batchList",batchList);
        return "A003";
    }

    @GetMapping({"/A003-03"})
    public ModelAndView batchSeeMore(@RequestParam("id") Integer id,Model model) throws ParseException {

        model.addAttribute("batch_id", id);
        return new ModelAndView("A003-03","chapterDTOList",chapterViewService.findAllChapterInChapterBatchByBatchId(id));
    }

    @GetMapping(path="/SendData")
    @ResponseBody
    public ResponseEntity SendData(@RequestParam("chpName") String chpName, @RequestParam("startDate")String startDate, @RequestParam("endDate")String endDate, @RequestParam("batchId")Integer batchId){
        chapterViewService.saveDatesForChapter(chpName,startDate,endDate,batchId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping({"/A003-01"})
    public String gotoAddBatch(Model model){
        List<Batch> batchList= batchService.findAllBatch();
        model.addAttribute("batchList",batchList);
        return "A003-01";
    }
}
