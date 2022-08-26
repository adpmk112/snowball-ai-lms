package com.ace.ai.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.datamodel.Chapter;
import com.ace.ai.admin.datamodel.ChapterFile;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.dtomodel.AdminChapterDTO;
import com.ace.ai.admin.dtomodel.FileUploadDTO;
import com.ace.ai.admin.repository.CourseRepository;
import com.ace.ai.admin.service.CourseService;

@Controller
@RequestMapping(value = "/admin/course")
public class CourseController {
    @Autowired
    CourseService courseService;



    @GetMapping("/chapter/add")
    public ModelAndView goToChapterAddPage(@RequestParam("courseId") int id,ModelMap model){
        
        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setCourseId(id);
        return new ModelAndView("A002-02","fileUploadDTO",fileUploadDTO);
    }
    @PostMapping("/chapter/addpost")
    public String uploadMultipartFile(@ModelAttribute("fileUploadDTO") FileUploadDTO fileUploadDTO, Model modal) {
        try {
            // Declare empty list for collect the files data
            // which will come from UI
            Chapter chapter = new Chapter();
            Course course = new Course();
            course.setId(fileUploadDTO.getCourseId());
            chapter.setCourse(course);
            chapter.setName(fileUploadDTO.getName());
            Chapter chapterSaved = courseService.saveChapter(chapter);
            chapterSaved.getName();
            
            int chapterId = courseService.getChapterId(chapterSaved.getName());
            Chapter toSetChapterId = new Chapter();
            toSetChapterId.setId(chapterId);

            for (MultipartFile vedio : fileUploadDTO.getVideo()) {
                ChapterFile chapterFile = new ChapterFile();
                String fileType = "vedio";
                String fileName = vedio.getOriginalFilename();

                chapterFile.setName(fileName);
                chapterFile.setFileType(fileType);
                chapterFile.setChapter(toSetChapterId);
                courseService.saveAllFilesList(chapterFile);
            }
            for (MultipartFile pdf : fileUploadDTO.getPdf()) {
                ChapterFile chapterFile = new ChapterFile();
                String fileType = "pdf";
                String fileName = pdf.getOriginalFilename();

                chapterFile.setName(fileName);
                chapterFile.setFileType(fileType);
                chapterFile.setChapter(toSetChapterId);
                courseService.saveAllFilesList(chapterFile);
            }

            for (MultipartFile assignment : fileUploadDTO.getAssignment()) {
                ChapterFile chapterFile = new ChapterFile();
                
                String fileName = assignment.getOriginalFilename();
                String fileType = "assignment";

                chapterFile.setName(fileName);
                chapterFile.setFileType(fileType);
                chapterFile.setChapter(toSetChapterId);
                courseService.saveAllFilesList(chapterFile);
            }
            
           
          

                String uploadDir = "./assets/chapterFiles/" + chapterSaved.getName();
                Path uploadPath = Paths.get(uploadDir);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
              
                    for (MultipartFile vedio : fileUploadDTO.getVideo()) {
                        if(!vedio.isEmpty()){
                        try (InputStream inputStream = vedio.getInputStream()) {
                            Path filePath = uploadPath.resolve(vedio.getOriginalFilename());
                            System.out.println(filePath.toFile().getAbsolutePath());
                            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new IOException("Could not save upload file: " + vedio.getOriginalFilename());
                        }
                    }
                    }
                
                
                for (MultipartFile pdf : fileUploadDTO.getPdf()) {
                    if(!pdf.isEmpty()){
                    try (InputStream inputStream = pdf.getInputStream()) {
                        Path filePath = uploadPath.resolve(pdf.getOriginalFilename());
                        System.out.println(filePath.toFile().getAbsolutePath());
                        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        throw new IOException("Could not save upload file: " + pdf.getOriginalFilename());
                    }
                }
            }
            
               
                for (MultipartFile assignment : fileUploadDTO.getAssignment()) {
                    if(!assignment.isEmpty()){
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
        return "redirect:/admin/course/chapter/add?courseId="+fileUploadDTO.getCourseId();
    }

    @GetMapping("")
    public ModelAndView getCourseList(ModelMap model) {
        List<Course> allCourse = courseService.getAllCourse();
        String courseCount = "Total : "+allCourse.size();
        model.addAttribute("courseCount", courseCount);
        return new ModelAndView("A002", "courseList", allCourse);
    }

    @GetMapping("/chapter/chapterFile")
    public ModelAndView getChapterDetail(@RequestParam("chapterId") int id,ModelMap model){
       
        return new ModelAndView("A002-03","chapterFileList", courseService.getChpaterFile(id));
    }

    @GetMapping("/chapter")
    public ModelAndView getCourseDetail(@RequestParam("courseId") int id, ModelMap model) {
        List<AdminChapterDTO> chapterList = courseService.getCourseDetail(id);
        for(AdminChapterDTO adminChapterDTO : chapterList){
            adminChapterDTO.setTotalFile(courseService.getChapterFileCount(adminChapterDTO.getId()));
        }
        String courseCount = "Total : "+courseService.getAllCourse().size();

        model.addAttribute("courseCount", courseCount);
        model.addAttribute("courseId",id);
        return new ModelAndView("A002-01", "chapterList", chapterList);
    }

    @PostMapping("/add")
    public String addCourse() {
        return "";
    }
}

