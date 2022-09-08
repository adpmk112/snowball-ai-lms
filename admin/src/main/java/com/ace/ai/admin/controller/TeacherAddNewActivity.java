package com.ace.ai.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.CustomChapter;
import com.ace.ai.admin.datamodel.CustomChapterFile;
import com.ace.ai.admin.dtomodel.NewActivityDTO;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.CustomChapterService;

@Controller
@RequestMapping("teacher/course")
public class TeacherAddNewActivity {
    @Autowired
    CustomChapterService customChapterService;
    @Autowired
    BatchService batchService;

    @GetMapping("/addNewActivity")
    public ModelAndView addNewActivity(@RequestParam("batchId") int batchId, ModelMap model) {
        NewActivityDTO newActivityDTO = new NewActivityDTO();
        newActivityDTO.setBatchId(batchId);
        return new ModelAndView("", "newActivityDTO", newActivityDTO);
    }

    @PostMapping("/addNewActivity")
    public String addNewActivityPost(@ModelAttribute("newActivityDTO") NewActivityDTO newActivityDTO, ModelMap model) {
        try {
            Batch batch = new Batch();
            CustomChapter customChapter = new CustomChapter();
            batch.setId(newActivityDTO.getBatchId());
            customChapter.setBatch(batch);
            customChapter.setName(newActivityDTO.getActivityName());

            customChapterService.createNewActivity(customChapter);

            int customChapterId = customChapterService.getChapterIdByName(newActivityDTO.getActivityName());
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
        return "redirect:/teacher/course/addNewActivity?batchId=" + newActivityDTO.getBatchId();
    }
}
