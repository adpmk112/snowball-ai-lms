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

import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.datamodel.RecordVideo;
import com.ace.ai.admin.dtomodel.RecordVideoDTO;
import com.ace.ai.admin.dtomodel.RecordVideoEditDTO;
import com.ace.ai.admin.repository.ClassRoomRepository;
import com.ace.ai.admin.service.RecordVideoService;

@Controller
@RequestMapping(value = "/teacher/batch/classroom/recordVideo")
public class RecordVideoController {

    @Autowired
    RecordVideoService recordVideoService;

    @Autowired
    ClassRoomRepository classRoomRepository;

    @GetMapping(value = "")
    public ModelAndView getRecordVideo(@RequestParam("classroomId") int classroomId, ModelMap model) {
        List<RecordVideo> recordVideoList = recordVideoService.getRecordVideoByClassroomId(classroomId);
        int batchId = classRoomRepository.getById(classroomId).getId();
        model.addAttribute("classroomId", classroomId);
        model.addAttribute("recordVideoList", recordVideoList);
        model.addAttribute("recordVideoEditDTO", new RecordVideoEditDTO());
        model.addAttribute("batchId",batchId);
        return new ModelAndView("T003-07", "recordVideoDTO", new RecordVideoDTO());
    }

    @PostMapping(value = "/add")
    public String addRecordVideo(@ModelAttribute("recordVideoDTO") RecordVideoDTO recordVideoDTO, ModelMap model)
            throws IOException {
        Classroom classroom = new Classroom();
        classroom.setId(recordVideoDTO.getClassroomId());
        for (MultipartFile video : recordVideoDTO.getRecordVideo()) {
            if (!video.isEmpty()) {
                RecordVideo recordVideo = new RecordVideo();
                recordVideo.setClassroom(classroom);
                recordVideo.setName(video.getOriginalFilename());
                recordVideoService.saveReordVideo(recordVideo);
            }
        }

        String uploadDir = "./assets/img/recordVideo/" + recordVideoDTO.getClassroomId();
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        for (MultipartFile video : recordVideoDTO.getRecordVideo()) {
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

        return "redirect:/teacher/batch/classroom/recordVideo?classroomId=" + recordVideoDTO.getClassroomId();
    }

    @PostMapping("/edit")
    public String editRecordVideo(@ModelAttribute("recordVideoEditDTO") RecordVideoEditDTO recordVideoEditDTO,
            ModelMap model) throws IOException {

        Classroom classroom = new Classroom();
        classroom.setId(recordVideoEditDTO.getClassroomId());
        RecordVideo recordVideo = new RecordVideo();
        // System.out.println(recordVideoEditDTO.getId()+"------------------------------------------------------------------------------------------");

        recordVideo.setId(recordVideoEditDTO.getId());
        recordVideo.setName(recordVideoEditDTO.getRecordVideo().getOriginalFilename());
        recordVideo.setClassroom(classroom);

        RecordVideo oldRecordVideo = recordVideoService.getRecordVideoById(recordVideoEditDTO.getId());

        Path path = Paths.get("./assets/img/recordVideo/" + recordVideoEditDTO.getClassroomId() + "/" + oldRecordVideo.getName());
        Files.delete(path);

        recordVideoService.saveReordVideo(recordVideo);

        String uploadDir = "./assets/img/recordVideo/" + recordVideoEditDTO.getClassroomId();
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        try (InputStream inputStream = recordVideoEditDTO.getRecordVideo().getInputStream()) {
            Path filePath = uploadPath.resolve(recordVideoEditDTO.getRecordVideo().getOriginalFilename());
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            try {
                throw new IOException(
                        "Could not save upload file: " + recordVideoEditDTO.getRecordVideo().getOriginalFilename());
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        }

        // model.addAttribute("msg","Update Successfully !!!");
        return "redirect:/teacher/batch/classroom/recordVideo?classroomId=" + recordVideoEditDTO.getClassroomId();

    }

    @GetMapping("/delete")
    public String deleteReocrdVideo(@RequestParam("recordVideoId")int recordVideoId,ModelMap model){
        int classroomId = recordVideoService.getRecordVideoById(recordVideoId).getClassroom().getId();
        recordVideoService.deleteRecordVideo(recordVideoId);
        
      
        
        return "redirect:/teacher/batch/classroom/recordVideo?classroomId=" + classroomId;
    }
}
