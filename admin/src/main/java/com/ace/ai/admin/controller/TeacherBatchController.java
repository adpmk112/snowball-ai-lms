package com.ace.ai.admin.controller;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/teacher/batch")
public class TeacherBatchController {
    @Autowired
    BatchService batchService;

    @GetMapping({ "/addNewActivity" })
    public String addNewActivity(Model model) {

        return "T003-01";
    }

    @GetMapping({ "/" })
    public String gotoBatch(Model model) {
        List<Batch> batchList = batchService.findAll();
        int totalBatch = batchList.size();
        model.addAttribute("totalBatch", totalBatch);
        model.addAttribute("batchList", batchList);
        return "T002";
}

    @GetMapping({ "/batchSeeMore" })
    public String batchSeeMore(Model model) {

        return "T003";
    }

}
