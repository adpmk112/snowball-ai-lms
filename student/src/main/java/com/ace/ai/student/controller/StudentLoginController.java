package com.ace.ai.student.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.dtomodel.StudentLoginDTO;
import com.ace.ai.student.service.StudentLoginService;

@Controller
@RequestMapping(value = "/student")
public class StudentLoginController {
    @Autowired
    StudentLoginService studentLoginService;

//    @GetMapping(value = "/login")
//    public ModelAndView studentLoginView(ModelMap model) {
//        model.addAttribute("error", "");
//        return new ModelAndView("", "studentLoginDTO", new StudentLoginDTO());
//    }
//
//    // @GetMapping(value = "/login/error")
//    // public ModelAndView studentLoginViewError(ModelMap model){
//    // return new ModelAndView("","studentLoginDTO",new StudentLoginDTO());
//    // }
//
//    @PostMapping(value = "/login/post")
//    public String studentLoginPost(@ModelAttribute("studentLoginDTO") StudentLoginDTO studentLoginDTO, ModelMap model){
//        boolean accExist = studentLoginService.studentAccExistCheck(studentLoginDTO.getCode(), studentLoginDTO.getPassword());
//        if(accExist == false){
//            model.addAttribute("error", "Login Fail!");
//            model.addAttribute("studentLoginDTO", studentLoginDTO);
//            return "";
//        }else{
//            Student student = studentLoginService.studnetLoginFindBatch(studentLoginDTO.getCode(), studentLoginDTO.getPassword());
//            if(student == null){
//                model.addAttribute("error", "There is no avaliable Batch!");
//                model.addAttribute("studentLoginDTO", studentLoginDTO);
//                return "";
//            }
//            else{
//                model.addAttribute("student", student);
//                return "redirect:/student/home";
//            }
//        }
//
//    }


}
