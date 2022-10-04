package com.ace.ai.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.CustomChapter;
import com.ace.ai.admin.datamodel.CustomChapterFile;
import com.ace.ai.admin.dtomodel.ChapterFileDTO;
import com.ace.ai.admin.dtomodel.ChapterRenameDTO;
import com.ace.ai.admin.dtomodel.CustomAssignmentDTO;
import com.ace.ai.admin.dtomodel.CustomChapterFileDTO;
import com.ace.ai.admin.dtomodel.NewActivityDTO;
import com.ace.ai.admin.repository.AssignmentRepository;
import com.ace.ai.admin.repository.CustomChapterFileRepository;
import com.ace.ai.admin.repository.CustomChapterRepository;
import com.ace.ai.admin.service.AssignmentService;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.CustomChapterService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
@RequestMapping("teacher/batch/course")
public class TeacherNewActivity {
    @Autowired
    CustomChapterService customChapterService;
    @Autowired
    BatchService batchService;
    @Autowired
    AssignmentService assignmentService;
    @Autowired
    CustomChapterRepository customChapterRepository;
    @Autowired
    CustomChapterFileRepository customChapterFileRepository;

    @GetMapping("/addActivity")
    public ModelAndView addNewActivity(@RequestParam("batchId") int batchId, ModelMap model) {
        NewActivityDTO newActivityDTO = new NewActivityDTO();
        newActivityDTO.setBatchId(batchId);
        List<CustomChapter>customChapterList = customChapterRepository.findByBatchIdAndDeleteStatus(batchId, false);
        model.addAttribute("batchId", batchId);
        model.addAttribute("totalCustomChapter",customChapterList.size());
        return new ModelAndView("T003-04", "newActivityDTO", newActivityDTO);
    }

    @GetMapping("/addActivity/success")
    public ModelAndView addNewActivitySuccess(@RequestParam("batchId") int batchId, ModelMap model) {
        NewActivityDTO newActivityDTO = new NewActivityDTO();
        newActivityDTO.setBatchId(batchId);
        List<CustomChapter>customChapterList = customChapterRepository.findByBatchIdAndDeleteStatus(batchId, false);
        model.addAttribute("batchId", batchId);
        model.addAttribute("totalCustomChapter",customChapterList.size());
        model.addAttribute("success","Chapter Added Successfully!");
        return new ModelAndView("T003-04", "newActivityDTO", newActivityDTO);
    }

    @PostMapping("/addActivityPost")
    public String addNewActivityPost(@ModelAttribute("newActivityDTO") NewActivityDTO newActivityDTO, ModelMap model) {
        try {
            Batch batch = new Batch();
            CustomChapter customChapter = new CustomChapter();
            batch.setId(newActivityDTO.getBatchId());
            customChapter.setBatch(batch);
            customChapter.setName(newActivityDTO.getActivityName());

            customChapterService.createNewActivity(customChapter);

            int customChapterId = customChapterService.getChapterIdByNameAndBatchId(newActivityDTO.getActivityName(),newActivityDTO.getBatchId());
            CustomChapter toSetCustomChapterId = new CustomChapter();
            toSetCustomChapterId.setId(customChapterId);

            for (MultipartFile video : newActivityDTO.getVideo()) {
                if (!video.isEmpty()) {
                    CustomChapterFile customChapterFile = new CustomChapterFile();
                    String fileType = "video";
                    String fileName = video.getOriginalFilename();

                    customChapterFile.setName(fileName);
                    customChapterFile.setFileType(fileType);
                    customChapterFile.setCustomChapter(toSetCustomChapterId);

                    customChapterService.saveCustomChapterFile(customChapterFile);
                }
            }
            for (MultipartFile pdf : newActivityDTO.getPdf()) {
                if (!pdf.isEmpty()) {
                    CustomChapterFile customChapterFile = new CustomChapterFile();
                    String fileType = "pdf";
                    String fileName = pdf.getOriginalFilename();

                    customChapterFile.setName(fileName);
                    customChapterFile.setFileType(fileType);
                    customChapterFile.setCustomChapter(toSetCustomChapterId);

                    customChapterService.saveCustomChapterFile(customChapterFile);
                }
            }

            for (MultipartFile assignment : newActivityDTO.getAssignment()) {
                if (!assignment.isEmpty()) {
                    CustomChapterFile customChapterFile = new CustomChapterFile();

                    String fileName = assignment.getOriginalFilename();
                    String fileType = "assignment";

                    customChapterFile.setName(fileName);
                    customChapterFile.setFileType(fileType);
                    customChapterFile.setCustomChapter(toSetCustomChapterId);

                    customChapterService.saveCustomChapterFile(customChapterFile);
                    assignmentService.customChapterAssignmentFileAdd(toSetCustomChapterId, batch.getId());
                }
            }

            String uploadDir = "./assets/img/customChapterFiles/" + customChapterId;
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile video : newActivityDTO.getVideo()) {
                if (!video.isEmpty()) {
                    try (InputStream inputStream = video.getInputStream()) {
                        Path filePath = uploadPath.resolve(video.getOriginalFilename());
                        System.out.println(filePath.toFile().getAbsolutePath());
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new IOException("Could not save upload file: " + video.getOriginalFilename());
                    }
                }
            }

            for (MultipartFile pdf : newActivityDTO.getPdf()) {
                if (!pdf.isEmpty()) {
                    try (InputStream inputStream = pdf.getInputStream()) {
                        Path filePath = uploadPath.resolve(pdf.getOriginalFilename());
                        System.out.println(filePath.toFile().getAbsolutePath());
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new IOException("Could not save upload file: " + pdf.getOriginalFilename());
                    }
                }
            }

            for (MultipartFile assignment : newActivityDTO.getAssignment()) {
                if (!assignment.isEmpty()) {
                    try (InputStream inputStream = assignment.getInputStream()) {
                        Path filePath = uploadPath.resolve(assignment.getOriginalFilename());
                        System.out.println(filePath.toFile().getAbsolutePath());
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new IOException("Could not save upload assignment: " + assignment.getOriginalFilename());
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/teacher/batch/course/addActivity/success?batchId=" + newActivityDTO.getBatchId();
    }

    @PostMapping("/activity/edit")
    public String editCustomChapter(@ModelAttribute("chapterRenameDTO") ChapterRenameDTO chapterRenameDTO,ModelMap model){
        customChapterService.editCustomChapter(chapterRenameDTO.getId(), chapterRenameDTO.getName());;
        return "redirect:/";
    }

    @GetMapping("/activityFile")
    public ModelAndView getActivityFiles(@RequestParam("customChapterId") int customChapterId, ModelMap model) {
        List<CustomChapterFile> customChapterFileList = customChapterService
                .getCustomChapterFileListById(customChapterId);
        int batchId = customChapterService.getCustomChapterById(customChapterId).getBatch().getId();
        String chapterName = customChapterService.getCustomChapterById(customChapterId).getName();
        model.addAttribute("batchId", batchId);
        model.addAttribute("customChapterId", customChapterId);
        model.addAttribute("customChapterFileDTO", new CustomChapterFileDTO());
        model.addAttribute("chapterName",chapterName);
        return new ModelAndView("T003-05", "customChapterFileList", customChapterFileList);
    }

    @PostMapping("/activityFile/add")
    public String addCustomChapterFile(@ModelAttribute("customChapterFileDTO") CustomChapterFileDTO customChapterFileDTO, ModelMap model)
            throws IOException {
        CustomChapter customChapter = customChapterService.getCustomChapterById(customChapterFileDTO.getCustomChapterId());

        CustomChapterFile customChapterFile = new CustomChapterFile();
        customChapterFile.setCustomChapter(customChapter);
        customChapterFile.setFileType(customChapterFileDTO.getFileType());
        customChapterFile.setName(customChapterFileDTO.getFile().getOriginalFilename());
        customChapterService.saveCustomChapterFile(customChapterFile);
        if(customChapterFile.getFileType().equalsIgnoreCase("assignment")){
            assignmentService.customChapterAssignmentPlus(customChapter, customChapter.getBatch().getId(),customChapterFile.getName());
        }

        Path uploadPath = Paths.get("./assets/img/customChapterFiles/" + customChapterFileDTO.getCustomChapterId());
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        try (InputStream inputStream = customChapterFileDTO.getFile().getInputStream()) {
            Path filePath = uploadPath.resolve(customChapterFileDTO.getFile().getOriginalFilename());
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            try {
                throw new IOException("Could not save upload file: " + customChapterFileDTO.getFile().getOriginalFilename());
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        }

        return "redirect:/teacher/batch/course/activityFile?customChapterId=" + customChapterFileDTO.getCustomChapterId();
    }

    @PostMapping("/activityFile/edit")
    public String editCustomChapterFile(@ModelAttribute("customChapterFileDTO") CustomChapterFileDTO customChapterFileDTO, ModelMap model)
            throws IOException {
        CustomChapter customChapter = customChapterService.getCustomChapterById(customChapterFileDTO.getCustomChapterId());
        CustomChapterFile customChapterFile = new CustomChapterFile();

        customChapterFile.setFileType(customChapterFileDTO.getFileType());
        customChapterFile.setId(customChapterFileDTO.getId());
        customChapterFile.setName(customChapterFileDTO.getFile().getOriginalFilename());
        customChapterFile.setCustomChapter(customChapter);

        CustomChapterFile oldCustomChapterFile = customChapterService.getCustomChapterFileById(customChapterFileDTO.getId());
        
        //Assignment assignment = 
        //assignmentService.getUniqueAssignment(customChapter, customChapter.getBatch().getId(), oldCustomChapterFile.getName());
        
        Path path = Paths
                .get("./assets/img/customChapterFiles/" + customChapterFileDTO.getCustomChapterId() + "/" + oldCustomChapterFile.getName());
        if(Files.exists(path)){
            Files.delete(path);
        }        
       
        customChapterService.saveCustomChapterFile(customChapterFile);
        

        //  Assignment assignment1 = 
        //  assignmentService.getUniqueAssignment(customChapter, customChapter.getBatch().getId(), customChapterFile.getName());
        
         //assignmentService.customChapterAssignmentEdit(assignment, assignment1.getName());

        String uploadDir = "./assets/img/customChapterFiles/" + customChapterFileDTO.getCustomChapterId();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        try (InputStream inputStream = customChapterFileDTO.getFile().getInputStream()) {
            Path filePath = uploadPath.resolve(customChapterFileDTO.getFile().getOriginalFilename());
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            try {
                throw new IOException("Could not save upload file: " + customChapterFileDTO.getFile().getOriginalFilename());
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        }

        model.addAttribute("msg", "Update Successfully !!!");
        return "redirect:/teacher/batch/course/activityFile?customChapterId=" + customChapterFileDTO.getCustomChapterId();

    }

    @GetMapping("/activityFile/delete")
    public String deleteCustomChapterFile(@RequestParam("customChapterFileId")int customChapterFileId,ModelMap model) throws IOException{
        int customChapterId = customChapterService.getCustomChapterFileById(customChapterFileId).getCustomChapter().getId();
        customChapterService.deleteCustomChapterFile(customChapterFileId);
        
         CustomChapter customChapter = customChapterRepository.findById(customChapterId).get();

         CustomChapterFile customChapterFile = customChapterFileRepository.findById(customChapterFileId).get();
         Path path = Paths
         .get("./assets/img/customChapterFiles/" + customChapterId + "/" + customChapterFile.getName());
 if(Files.exists(path)){
     Files.delete(path);
 } 
        //  Assignment assignment = 
        //  assignmentService.getUniqueAssignment(customChapter, customChapter.getBatch().getId(), customChapterFile.getName());

        //  assignmentService.customChapterAssignmentDelete(assignment);

        return "redirect:/teacher/batch/course/activityFile?customChapterId=" + customChapterId;
    }

}
