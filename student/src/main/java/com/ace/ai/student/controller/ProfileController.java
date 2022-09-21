package com.ace.ai.student.controller;

import com.ace.ai.student.config.StudentUserDetails;
import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.dtomodel.ChangePasswordDTO;
import com.ace.ai.student.dtomodel.StudentDTO;
import com.ace.ai.student.service.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class ProfileController {
    @Autowired
    StudentProfileService studentProfileService;


    @GetMapping("/student/updateProfileSetUp/")
    public ModelAndView adminProfile(@AuthenticationPrincipal StudentUserDetails userDetails, Model model){
        Student student=studentProfileService.findByCode(userDetails.getCode());
        StudentDTO studentDTO= new StudentDTO();
        studentDTO.setName(student.getName());
        studentDTO.setCode(student.getCode());


        if(student.getPhoto()!=null){
            model.addAttribute("photoName",student.getPhoto());

        }
        else{
            model.addAttribute("photoName","default");
        }

        return new ModelAndView("S004","studentDTO",studentDTO);
    }

    @PostMapping("/student/profileUpdate/")
    public ModelAndView adminProfileUpdate(Model model, @ModelAttribute("adminDTO") @Validated StudentDTO studentDTO, BindingResult bs, @AuthenticationPrincipal StudentUserDetails userDetails) throws IOException {
        Student student=studentProfileService.findByCode(userDetails.getCode());
        if (bs.hasErrors()) {
            studentDTO.setName(student.getName());
            studentDTO.setCode(student.getCode());
            model.addAttribute("photoName",student.getPhoto());
        }
        else{
            if(studentProfileService.checkPassword(studentDTO.getPassword(),userDetails.getPassword())){
                studentDTO.setCode(student.getCode());
                model.addAttribute("success","Success");
                String capitalizedName=studentProfileService.capitalize(studentDTO.getName());
                studentDTO.setName(capitalizedName);
                studentProfileService.saveAdmin(studentDTO);
                userDetails.setName(studentDTO.getName());
                if(studentDTO.getPhoto().isEmpty()){
                    if(student.getPhoto()!=null){
                        userDetails.setPhoto(student.getPhoto());
                        model.addAttribute("photoName",student.getPhoto());
                    }
                }
                else {
                    userDetails.setPhoto(studentDTO.getPhoto().getOriginalFilename());
                    model.addAttribute("photoName",studentDTO.getPhoto().getOriginalFilename());
                }

            }
            else{
                studentDTO.setCode(student.getCode());
                model.addAttribute("photoName",student.getPhoto());
                model.addAttribute("error","Wrong password!");
            }

        }

        return new ModelAndView("S004","studentDTO",studentDTO);
    }

    @GetMapping("/student/changePasswordSetUp/")
    public ModelAndView adminChangePasswordSetUp(Model model){
        return new ModelAndView("S006","changePassDTO",new ChangePasswordDTO());

    }

    @PostMapping("/student/changePassword/")
    public ModelAndView adminChangePassword(@AuthenticationPrincipal StudentUserDetails userDetails, Model model, @ModelAttribute("changePassDTO") @Validated ChangePasswordDTO changePassDTO, BindingResult br, RedirectAttributes redirect){
        if(br.hasErrors()){

        }
        else {
            if(!studentProfileService.checkPassword(changePassDTO.getOldPassword(),userDetails.getPassword()))
            {
                model.addAttribute("msg","Wrong Password");
            }
            else if(!changePassDTO.getNewPassword().equals(changePassDTO.getConfirmPassword())){
                model.addAttribute("error","passwords must be equal");
            }
            else if(studentProfileService.checkPassword(changePassDTO.getNewPassword(),userDetails.getPassword())){
                model.addAttribute("newPass","You've entered your old password!");
            }
            else{
                studentProfileService.saveStudentPassword(changePassDTO.getNewPassword(),userDetails.getCode());
                redirect.addFlashAttribute("success","You have reset your password successfully! You may now login.");
                return new ModelAndView("redirect:/student/login/","",null);
            }
        }
        return new ModelAndView("S006","changePassDTO",changePassDTO);

    }


}

