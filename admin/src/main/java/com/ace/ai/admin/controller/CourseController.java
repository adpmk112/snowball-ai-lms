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
import java.util.Optional;

import com.ace.ai.admin.datamodel.*;
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

import com.ace.ai.admin.dtomodel.AdminChapterDTO;
import com.ace.ai.admin.dtomodel.ChapterFileDTO;
import com.ace.ai.admin.dtomodel.ChapterRenameDTO;
import com.ace.ai.admin.dtomodel.CourseDTO;
import com.ace.ai.admin.dtomodel.FileUploadDTO;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.ChapterRepository;
import com.ace.ai.admin.service.AssignmentService;
import com.ace.ai.admin.service.ChapterBatchService;
import com.ace.ai.admin.service.CourseService;
import com.ace.ai.admin.service.ExamFormService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping(value = "/admin/course")
@Slf4j
public class CourseController {
    @Autowired
    CourseService courseService;

    @Autowired
    ExamFormService examFormService;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    AssignmentService assignmentService;

    @Autowired
    ChapterBatchService chapterBatchService;

    @Autowired
    BatchRepository batchRepository;

    @GetMapping("/chapter/add")
    public ModelAndView goToChapterAddPage(@RequestParam("courseId") int id, ModelMap model) {

        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setCourseId(id);
        model.addAttribute("courseId", id);
        List<Course> allCourse = courseService.getAllCourse();
        String courseCount = "Total : " + allCourse.size();
        model.addAttribute("courseCount", courseCount);

        return new ModelAndView("A002-02", "fileUploadDTO", fileUploadDTO);
    }

    @GetMapping("/chapter/add/success")
    public ModelAndView goToChapterAddPageSuccess(@RequestParam("courseId") int id, ModelMap model) {

        FileUploadDTO fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setCourseId(id);
        model.addAttribute("courseId", id);
        List<Course> allCourse = courseService.getAllCourse();
        String courseCount = "Total : " + allCourse.size();
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("success","Chapter Added Successfully!");
        return new ModelAndView("A002-02", "fileUploadDTO", fileUploadDTO);
    }

    @PostMapping("/chapter/addpost")
    public String uploadMultipartFile(@ModelAttribute("fileUploadDTO") FileUploadDTO fileUploadDTO, Model modal) {
        try {
            
            Course course = new Course();
            course.setId(fileUploadDTO.getCourseId());

            Chapter chapter = new Chapter();
            chapter.setCourse(course);
            chapter.setName(fileUploadDTO.getName());

            courseService.saveChapter(chapter);


            int chapterId = courseService.getChapterIdByNameAndCourseId(fileUploadDTO.getName(),fileUploadDTO.getCourseId());
            Chapter chapter1 = courseService.getChapterById(chapterId);
            List<Batch> batchList = courseService.getBatchListByCourseId(chapter1.getCourse().getId());
            if(batchList!=null) {
                for (Batch batch : batchList) {
                    ChapterBatch chapterBatch = new ChapterBatch();
                    chapterBatch.setChapter(chapter1);
                    chapterBatch.setBatch(batch);
                    courseService.saveChapterBatch(chapterBatch);
                }
            }
            Chapter toSetChapterId = new Chapter();
            toSetChapterId.setId(chapterId);

            for (MultipartFile video : fileUploadDTO.getVideo()) {
                if (!video.isEmpty()) {
                ChapterFile chapterFile = new ChapterFile();
                String fileType = "video";
                String fileName = video.getOriginalFilename();

                chapterFile.setName(fileName);
                chapterFile.setFileType(fileType);
                chapterFile.setChapter(toSetChapterId);
                System.out.println(chapterFile);
                courseService.saveFile(chapterFile);

                }
            }
            for (MultipartFile pdf : fileUploadDTO.getPdf()) {
                if (!pdf.isEmpty()) {
                ChapterFile chapterFile = new ChapterFile();
                String fileType = "pdf";
                String fileName = pdf.getOriginalFilename();

                chapterFile.setName(fileName);
                chapterFile.setFileType(fileType);
                chapterFile.setChapter(toSetChapterId);
                System.out.println(chapterFile);
                courseService.saveFile(chapterFile);
                }
            }

            for (MultipartFile assignment : fileUploadDTO.getAssignment()) {
                if (!assignment.isEmpty()) {
                ChapterFile chapterFile = new ChapterFile();

                String fileName = assignment.getOriginalFilename();
                String fileType = "assignment";

                chapterFile.setName(fileName);
                chapterFile.setFileType(fileType);
                chapterFile.setChapter(toSetChapterId);
                System.out.println(chapterFile);
                courseService.saveFile(chapterFile);

                }
            }

            String uploadDir = "./assets/img/chapterFiles/" + chapterId;
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            for (MultipartFile video : fileUploadDTO.getVideo()) {
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

            for (MultipartFile pdf : fileUploadDTO.getPdf()) {
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

            for (MultipartFile assignment : fileUploadDTO.getAssignment()) {
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
        return "redirect:/admin/course/chapter/add/success?courseId=" + fileUploadDTO.getCourseId();
    }

    @GetMapping("")
    public ModelAndView getCourseList(ModelMap model) {
        List<Course> allCourse = courseService.getAllCourse();
        String courseCount = "Total : " + allCourse.size();
        model.addAttribute("courseCount", courseCount);
        model.addAttribute("courseList",allCourse);
        return new ModelAndView("A002", "courseDTO", new CourseDTO());
    }

     // All Course And Exams need to add request param "courseId" and "radio"
     @GetMapping("/courseDetail")
     public String getCourseDetail(@RequestParam("courseId") int courseId, ModelMap model, @RequestParam("radio") String radio) {
         List<ExamForm> exams = examFormService.findByDeleteStatusAndCourseId(false, courseId);
         model.addAttribute("radioButton", radio);
         model.addAttribute("examList", exams);
         model.addAttribute("courseId", courseId);
         model.addAttribute("courseName", courseService.getById(courseId).getName());
         // Add Chapters
         List<AdminChapterDTO> chapterList = courseService.getCourseDetail(courseId);
         for (AdminChapterDTO adminChapterDTO : chapterList) {
             adminChapterDTO.setTotalFile(courseService.getChapterFileCount(adminChapterDTO.getId()));
         }
         String courseCount = "Total : " + courseService.getAllCourse().size();
         model.addAttribute("courseCount", courseCount);
         model.addAttribute("chapterList", chapterList);
        //  System.out.println("exam length => "+ exams.size());
        //  System.out.println("course length => "+ chapterList.size());
         return "A002-01";
     }

    @PostMapping("/add")
    public String addCourse(@ModelAttribute("courseDTO") CourseDTO courseDTO, Model modal) {
        courseService.saveCourse(courseDTO.getName());
        return "redirect:/admin/course";
    }

    @GetMapping("/chapter/chapterFile")
    public ModelAndView getChapterFiles(@RequestParam("chapterId") int id, ModelMap model) {
        List<Course> allCourse = courseService.getAllCourse();
        String courseCount = "Total : " + allCourse.size();
        model.addAttribute("courseCount", courseCount);
       List<ChapterFileDTO> chapterFileList = courseService.getChapterFile(id);
       int courseId = courseService.getChapterById(id).getCourse().getId();
       model.addAttribute("courseId", courseId);
        model.addAttribute("chapterId", id);
        model.addAttribute("chapterFileDTO",new ChapterFileDTO());
        return new ModelAndView("A002-03", "chapterFileList", chapterFileList);
    }

    @GetMapping("/chapter/chapterFile/delete")
    public String deleteChapterFile(@RequestParam("chapterFileId")int chapterFileId,ModelMap model){
        int chapterId = courseService.getOneChapterFile(chapterFileId).getChapter().getId();
        courseService.deleteChapterFile(chapterFileId);
        List<Course> allCourse = courseService.getAllCourse();
        String courseCount = "Total : " + allCourse.size();
        model.addAttribute("courseCount", courseCount);
      
        
        return "redirect:/admin/course/chapter/chapterFile?chapterId=" + chapterId;
    }

    @GetMapping("/chapter/delete")
    public String deleteChapter(@RequestParam("chapterId")int id,ModelMap model){
        int courseId = courseService.getChapterById(id).getCourse().getId();
        courseService.deleteChapter(id);
        List<Course> allCourse = courseService.getAllCourse();
        String courseCount = "Total : " + allCourse.size();
        model.addAttribute("courseCount", courseCount);


        Chapter chapter1 = courseService.getChapterById(id);
            List<Batch> batchList = courseService.getBatchListByCourseId(chapter1.getCourse().getId());
            if(batchList!=null) {
                for (Batch batch : batchList) {
                    ChapterBatch chapterBatch = courseService.getChapterBatchByChapterIdAndBAtchId(chapter1.getId(), batch.getId());
                   
                    chapterBatch.setDeleteStatus(1);
                    courseService.saveChapterBatch(chapterBatch);
                }
            }


        return "redirect:/admin/course/courseDetail?courseId=" + courseId + "&radio=chapter";
    }
    
    @GetMapping("/delete")
    public String deleteCourse(@RequestParam("courseId")int id,ModelMap model){
        courseService.deleteCourse(id);
        List<Course> allCourse = courseService.getAllCourse();
        String courseCount = "Total : " + allCourse.size();
        model.addAttribute("courseCount", courseCount);
        return "redirect:/admin/course";
        
        
    }
    
    @PostMapping("/editpost")
    public String editCourse(@ModelAttribute("courseDTO") CourseDTO courseDTO,ModelMap model){
        courseService.editCourse(courseDTO.getName(), courseDTO.getId());
        return "redirect:/admin/course";
    }

    @PostMapping("/chapter/editpost")
    public String editChapter(@ModelAttribute("chapterRenameDTO") ChapterRenameDTO chapterRenameDTO,ModelMap model){
        courseService.editChapter(chapterRenameDTO.getId(), chapterRenameDTO.getName());;
        return "redirect:/admin/course/courseDetail?courseId=" + chapterRenameDTO.getCourseId() + "&radio=chapter";
    }
    // @GetMapping("/chapter/chapterFile/edit")
    // public ModelAndView getChapterFileToEdit(@RequestParam("chapterFileId")int id,ModelMap model){
    //     courseService.(id);
    //     return "A002-03";
    // }

    // @GetMapping("/chapter/chapterFile/edit")
    // public ModelAndView getEditChapterFile(@RequestParam("chapterFileId") int id,ModelMap model){
    //         return new ModelAndView("","chapterFile",courseService.getOneChapterFile(id));
    // }

        @PostMapping("/chapter/chapterFile/addpost")
        public String addChapterFile(@ModelAttribute("chapterFile") ChapterFileDTO chapterFileDTO,ModelMap model) throws IOException{
            Chapter chapter = new Chapter();
        chapter.setId(chapterFileDTO.getChapterId());
            ChapterFile chapterFile = new ChapterFile();
            chapterFile.setChapter(chapter);
            chapterFile.setFileType(chapterFileDTO.getFileType());
            chapterFile.setName(chapterFileDTO.getFile().getOriginalFilename());
            courseService.saveFile(chapterFile);

            
            Path uploadPath = Paths.get("./assets/img/chapterFiles/"+chapterFileDTO.getChapterId());
            if(!Files.exists(uploadPath)){
                try {
                  Files.createDirectories(uploadPath);
                } catch (IOException e) {
                  
                  e.printStackTrace();
                }
                }
              try( InputStream inputStream=chapterFileDTO.getFile().getInputStream()){
                Path filePath=uploadPath.resolve(chapterFileDTO.getFile().getOriginalFilename());
                System.out.println(filePath.toFile().getAbsolutePath());
                Files.copy(inputStream, filePath ,StandardCopyOption.REPLACE_EXISTING);
              }catch (IOException e){
                  try {
                    throw new IOException("Could not save upload file: " + chapterFileDTO.getFile().getOriginalFilename());
                  } catch (IOException e1) {
                    
                    e1.printStackTrace();
                  }
              } 

              return "redirect:/admin/course/chapter/chapterFile?chapterId=" + chapterFileDTO.getChapterId();
        }


    @PostMapping("/chapter/chapterFile/editpost")
    public String editChapterFile(@ModelAttribute("chapterFile") ChapterFileDTO chapterFileDTO,ModelMap model) throws IOException{
        Chapter chapter = new Chapter();
        chapter.setId(chapterFileDTO.getChapterId());
      ChapterFile chapterFile=new ChapterFile();
      System.out.println(chapterFileDTO.getId()+"------------------------------------------------------------------------------------------");
      chapterFile.setFileType(chapterFileDTO.getFileType());
      chapterFile.setId(chapterFileDTO.getId());
      chapterFile.setName(chapterFileDTO.getFile().getOriginalFilename());
      chapterFile.setChapter(chapter);
      
      
            ChapterFile oldChapterFile = courseService.getOneChapterFile(chapterFileDTO.getId());
            

            Path path = Paths.get("./assets/img/chapterFiles/"+ chapterFileDTO.getChapterId()+"/"+oldChapterFile.getName());
            Files.delete(path);
           
          
            courseService.saveFile(chapterFile);

            
            String uploadDir="./assets/img/chapterFiles/"+ chapterFileDTO.getChapterId();
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
            try {
              Files.createDirectories(uploadPath);
            } catch (IOException e) {
              
              e.printStackTrace();
            }
            }
          try( InputStream inputStream=chapterFileDTO.getFile().getInputStream()){
            Path filePath=uploadPath.resolve(chapterFileDTO.getFile().getOriginalFilename());
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath ,StandardCopyOption.REPLACE_EXISTING);
          }catch (IOException e){
              try {
                throw new IOException("Could not save upload file: " + chapterFileDTO.getFile().getOriginalFilename());
              } catch (IOException e1) {
                
                e1.printStackTrace();
              }
          } 
          
          model.addAttribute("msg","Update Successfully !!!");
          return "redirect:/admin/course/chapter/chapterFile?chapterId=" + chapterFileDTO.getChapterId();
        
        
      }

}
