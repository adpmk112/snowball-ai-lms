package com.ace.ai.student.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.student.config.StudentUserDetails;
import com.ace.ai.student.datamodel.Assignment;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.datamodel.StudentAssignmentMark;
import com.ace.ai.student.dtomodel.AssignmentDateTimeDTO;
import com.ace.ai.student.dtomodel.AssignmentFileDTO;
import com.ace.ai.student.dtomodel.AssignmentMarkDTO;
import com.ace.ai.student.dtomodel.StuCommentPostDTO;
import com.ace.ai.student.dtomodel.StuCommentViewDTO;
import com.ace.ai.student.dtomodel.StuReplyPostDTO;
import com.ace.ai.student.dtomodel.TeacherAssignmentViewDTO;
import com.ace.ai.student.repository.StudentAssignmentMarkRepository;
import com.ace.ai.student.service.AssignmentService;
import com.ace.ai.student.service.StudentCommentService;
import com.ace.ai.student.service.StudentCourseService;

@Controller
@RequestMapping("/student")
public class AssignmentController {

  @Autowired
  AssignmentService assignmentService;
  @Autowired
  StudentAssignmentMarkRepository studentAssignmentMarkRepository;
  @Autowired
  StudentCommentService studentCommentService;
  @Autowired
  StudentCourseService studentCourseService;

  @GetMapping("/assignmentView")
  public ModelAndView assignmentStudent(@AuthenticationPrincipal StudentUserDetails userDetails,
      @RequestParam("assignmentId") Integer assignmentId, @RequestParam("studentId") Integer studentId,
      @RequestParam("chapterId") Integer chapterId, ModelMap model) throws ParseException {
    AssignmentDateTimeDTO assignmentDateTimeDTO = assignmentService.getDateTimeByAssignmentId(assignmentId);
    AssignmentMarkDTO assignmentMarkDTO = assignmentService.getStudentMarkByAssiIdAndStuId(assignmentId, studentId);
    String status = assignmentService.getStatusAssignmentId(assignmentId, studentId);
    model.addAttribute("assignmentDateTimeDTO", assignmentDateTimeDTO);
    model.addAttribute("assignmentMarkDTO", assignmentMarkDTO);
    AssignmentFileDTO assignmentFileDTO = new AssignmentFileDTO();
    assignmentFileDTO.setAssignmentId(assignmentId);
    assignmentFileDTO.setStudentId(studentId);
    model.addAttribute("status", status);
    model.addAttribute("chapterId", chapterId);
    model.addAttribute("assignmentId", assignmentId);
    model.addAttribute("assignmentName", studentCommentService.getAssignmentById(assignmentId).getName());

    Student student = studentCourseService.getStudentById(userDetails.getId());
    TeacherAssignmentViewDTO teacherAssignmentViewDTO = new TeacherAssignmentViewDTO();
    teacherAssignmentViewDTO.setAssignmentId(assignmentId);
    teacherAssignmentViewDTO.setBatchId(student.getBatch().getId());
    teacherAssignmentViewDTO.setStuCode(student.getCode());
    teacherAssignmentViewDTO
        .setTeacherCode(studentCommentService.getTeacherCodeListByBatchId(student.getBatch().getId()));
    List<StuCommentViewDTO> stuCommentViewDTOList = studentCommentService
        .getCommentListByBatchIdAndLocationAndCommenterCode(teacherAssignmentViewDTO);
    model.addAttribute("batchId", student.getBatch().getId());
    model.addAttribute("stuId", student.getId());
    model.addAttribute("stuCode", student.getCode());

    model.addAttribute("stuReplyPostDTO", new StuReplyPostDTO());
    model.addAttribute("stuCommentPostDTO", new StuCommentPostDTO());
    model.addAttribute("stuCommentViewDTOList", stuCommentViewDTOList);
    model.addAttribute("error", "File cannot be blank!!!!");

    return new ModelAndView("S001-03", "assignmentFileDTO", assignmentFileDTO);
  }

  @PostMapping("/assignmentAdd")
  public String assignmentAdd(@ModelAttribute("assignmentFileDTO") AssignmentFileDTO assignmentFileDTO, ModelMap model)
      throws ParseException, IOException {
    LocalDate localDate = LocalDate.now();
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String currentDate = localDate.format(dateFormatter);
    LocalTime localTime = LocalTime.now();
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
    String currentTime = localTime.format(timeFormatter);
    String submitTime = assignmentService.englishTime(currentTime);
    String fileName = StringUtils.cleanPath(assignmentFileDTO.getAssignmentFile().getOriginalFilename());
    StudentAssignmentMark checkStudentAssignmentMark = studentAssignmentMarkRepository
        .findByAssignment_IdAndStudent_Id(assignmentFileDTO.getAssignmentId(), assignmentFileDTO.getStudentId());
    StudentAssignmentMark studentAssignmentMark = new StudentAssignmentMark();
    Assignment assignment = new Assignment();
    assignment.setId(assignmentFileDTO.getAssignmentId());
    Student student = new Student();
    student.setId(assignmentFileDTO.getStudentId());

    if (checkStudentAssignmentMark == null) {
     
      studentAssignmentMark.setUploadedFile(fileName);
      studentAssignmentMark.setDate(currentDate);
      studentAssignmentMark.setTime(submitTime);
      
      studentAssignmentMark.setNotification(true);
    } else {
      StudentAssignmentMark studentAssignmentMark2 = assignmentService
          .getStudentAssignmentMarkUnique(assignmentFileDTO.getAssignmentId(), assignmentFileDTO.getStudentId());

      Path path = Paths.get("./assets/img/assignmentFiles/" + studentAssignmentMark2.getAssignment().getId() + "/"
          + studentAssignmentMark2.getStudent().getId() + "/"
          + studentAssignmentMark2.getUploadedFile());
          if (Files.exists(path)) {
            Files.delete(path);
          }
          studentAssignmentMark.setNotification(true);
          studentAssignmentMark.setDate(currentDate);
          studentAssignmentMark.setTime(submitTime);
          studentAssignmentMark.setUploadedFile(fileName);
          studentAssignmentMark.setId(checkStudentAssignmentMark.getId());

      }
      studentAssignmentMark.setAssignment(assignment);
      studentAssignmentMark.setStudent(student);
      studentAssignmentMarkRepository.save(studentAssignmentMark);
      Path uploadPath = Paths.get("./assets/img/assignmentFiles/" + assignment.getId() + "/" + student.getId());


    if (!Files.exists(uploadPath)) {
      try {
        Files.createDirectories(uploadPath);
      } catch (IOException e) {

        e.printStackTrace();
      }

    }
    try (InputStream inputStream = assignmentFileDTO.getAssignmentFile().getInputStream()) {
      Path filePath = uploadPath.resolve(fileName);
      System.out.println(filePath.toFile().getAbsolutePath());
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException e) {
      try {
        throw new IOException("Could not save upload file: " + fileName);
      } catch (IOException e1) {

        e1.printStackTrace();
      }
    }
    AssignmentDateTimeDTO assignmentDateTimeDTO = assignmentService
        .getDateTimeByAssignmentId(assignmentFileDTO.getAssignmentId());
    AssignmentMarkDTO assignmentMarkDTO = assignmentService
        .getStudentMarkByAssiIdAndStuId(assignmentFileDTO.getAssignmentId(), assignmentFileDTO.getStudentId());
    assignmentMarkDTO.setSubmitDate(currentDate);
    assignmentMarkDTO.setSubmitTime(currentTime);
    String status = assignmentService.getStatusAssignment(assignmentFileDTO.getAssignmentId());

    model.addAttribute("assignmentDateTimeDTO", assignmentDateTimeDTO);
    model.addAttribute("assignmentMarkDTO", assignmentMarkDTO);
    model.addAttribute("status", status);
    return "redirect:/student/assignmentView?assignmentId=" + assignmentFileDTO.getAssignmentId() + "&studentId="
        + assignmentFileDTO.getStudentId() + "&chapterId=" + assignmentFileDTO.getChapterId();

  }

  @PostMapping(value = "/assignment/commentpost")
  public String postVideoCommment(@ModelAttribute("stuCommentPostDTO") StuCommentPostDTO stuCommentPostDTO,
      ModelMap model) {
    // stuCommentPostDTO.setLocation("home");
    studentCommentService.saveComment(stuCommentPostDTO);
    return "redirect:/student/assignmentView?assignmentId=" + stuCommentPostDTO.getLocationId() + "&studentId="
        + stuCommentPostDTO.getStuId() + "&chapterId=" + stuCommentPostDTO.getChapterId();
  }

  @PostMapping(value = "/assignment/replypost")
  public String postVideoReply(@ModelAttribute("stuReplyPostDTO") StuReplyPostDTO stuReplyPostDTO, ModelMap model) {

    studentCommentService.saveReply(stuReplyPostDTO);

    return "redirect:/student/assignmentView?assignmentId=" + stuReplyPostDTO.getLocationId() + "&studentId="
        + stuReplyPostDTO.getStuId() + "&chapterId=" + stuReplyPostDTO.getChapterId();
  }

}
