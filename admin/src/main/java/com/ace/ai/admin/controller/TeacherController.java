package com.ace.ai.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.TeacherDTO;
import com.ace.ai.admin.repository.TeacherRepository;
import com.ace.ai.admin.service.TeacherService;


@Controller
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

  @Autowired
  private TeacherRepository teacherRepository;

  @GetMapping("/setupAddTeacher")
   public ModelAndView setupAddTeacher() {
    return new ModelAndView("A004-01","teacherDto",new TeacherDTO());
  }

  @PostMapping("/A004-01")
       public String addTeacher(@ModelAttribute("teacherDto") TeacherDTO teacherDto, ModelMap model) throws IllegalStateException, IOException {
       Teacher bean=new Teacher();
       bean.setCode(teacherDto.getCode());
       bean.setName(teacherDto.getName());
       bean.setPassword(teacherDto.getPassword());
       String fileName=StringUtils.cleanPath(teacherDto.getPhoto().getOriginalFilename());
       bean.setPhoto(fileName);

       Teacher savedTeacher=teacherRepository.save(bean);

       String uploadDir="./assets/img/"+ savedTeacher.getCode();

       Path uploadPath = Paths.get(uploadDir);

       if(!Files.exists(uploadPath)){
        Files.createDirectories(uploadPath);
       }

      try( InputStream inputStream=teacherDto.getPhoto().getInputStream()){
       Path filePath=uploadPath.resolve(fileName);
       System.out.println(filePath.toFile().getAbsolutePath());
       Files.copy(inputStream, filePath ,StandardCopyOption.REPLACE_EXISTING);
      }catch (IOException e){
          throw new IOException("Could not save upload file: " + fileName);
      }

       return "A004-01";
}

  @GetMapping("/A004")
    public String showTeacher(ModelMap model){
      List<Teacher> teacherList=teacherService.findAllTeacher();
      model.addAttribute("teacherList",teacherList);
      return "A004";
    }
  
  @GetMapping("/setupUpdateTeacher")
   public ModelAndView updateTeacher(ModelMap model,@RequestParam("code")String code, HttpServletRequest request) throws ParseException{
      Teacher bean = teacherService.getCode(code);
      System.out.println(bean);
      request.setAttribute("news_img", bean.getPhoto());
      return new ModelAndView("A004-02","bean",bean);
   }
}
