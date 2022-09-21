package com.ace.ai.student.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.student.datamodel.Assignment;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.datamodel.StudentAssignmentMark;
import com.ace.ai.student.dtomodel.AssignmentDateTimeDTO;
import com.ace.ai.student.dtomodel.AssignmentFileDTO;
import com.ace.ai.student.dtomodel.StudentMarkDTO;
import com.ace.ai.student.repository.StudentAssignmentMarkRepository;
import com.ace.ai.student.service.AssignmentService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/student")
@Slf4j
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;
    @Autowired
    StudentAssignmentMarkRepository studentAssignmentMarkRepository;

    @GetMapping("/assignmentView")
    public ModelAndView assignmentStudent(@RequestParam("assignmentId") Integer assignmentId,@RequestParam("studentId") Integer studentId,ModelMap model){
        AssignmentDateTimeDTO assignmentDateTimeDTO = assignmentService.getDateTimeByAssignmentId(assignmentId);
        StudentMarkDTO studentMarkDTO = assignmentService.getStudentMarkByAssiIdAndStuId( assignmentId, studentId);
        model.addAttribute("assignmentDateTimeDTO" ,assignmentDateTimeDTO);
        model.addAttribute("studentMarkDTO", studentMarkDTO);
       AssignmentFileDTO assignmentFileDTO = new AssignmentFileDTO();
       assignmentFileDTO.setAssignmentId(assignmentId);
       assignmentFileDTO.setStudentId(studentId);
        return new ModelAndView("S001-03","assignmentFileDTO",assignmentFileDTO);
    }

    @PostMapping("/assignmentAdd")
    public String assignmentAdd(@ModelAttribute("assignmentFileDTO") AssignmentFileDTO assignmentFileDTO, ModelMap model){
      System.out.println("Assignment DTO______________________"+ assignmentFileDTO);
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String localDateString = localDate.format(dateTimeFormatter);
        LocalTime localTime = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        String currentTime = localTime.format(timeFormatter);
        StudentAssignmentMark  studentAssignmentMark = new StudentAssignmentMark();
        String fileName=assignmentFileDTO.getAssignmentFile().getOriginalFilename();
        studentAssignmentMark.setUploadedFile(fileName);
        studentAssignmentMark.setDate(localDateString);
        studentAssignmentMark.setTime(currentTime);
        Assignment assignment = new Assignment();
        log.info("assignmentid -->"+assignmentFileDTO.getAssignmentId());
        assignment.setId(assignmentFileDTO.getAssignmentId());
        studentAssignmentMark.setAssignment(assignment);
        Student student =  new Student();
        student.setId(assignmentFileDTO.getStudentId());
        studentAssignmentMark.setStudent(student);
        StudentAssignmentMark studentAssignmentMarkSaved = studentAssignmentMarkRepository.save(studentAssignmentMark);
        
        Path uploadPath = Paths.get("./assets/img/assignmentFiles/"+assignment.getId()+"/"+student.getCode());
        if(!Files.exists(uploadPath)){
            try {
              Files.createDirectories(uploadPath);
            } catch (IOException e) {
              
              e.printStackTrace();
            }
            }
          try( InputStream inputStream=assignmentFileDTO.getAssignmentFile().getInputStream()){
            Path filePath=uploadPath.resolve(assignmentFileDTO.getAssignmentFile().getOriginalFilename());
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath ,StandardCopyOption.REPLACE_EXISTING);
          }catch (IOException e){
              try {
                throw new IOException("Could not save upload file: " + assignmentFileDTO.getAssignmentFile().getOriginalFilename());
              } catch (IOException e1) {
                
                e1.printStackTrace();
              }
          } 
          return "redirect:/student/assignmentAdd/successfulAdd";
    }

    
}
