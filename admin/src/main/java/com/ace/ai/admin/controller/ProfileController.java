package com.ace.ai.admin.controller;

import com.ace.ai.admin.config.AdminUserDetails;
import com.ace.ai.admin.config.TeacherUserDetails;
import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.AdminDTO;
import com.ace.ai.admin.dtomodel.ChangePasswordDTO;
import com.ace.ai.admin.dtomodel.TeacherDTO;
import com.ace.ai.admin.service.AdminProfileService;
import com.ace.ai.admin.service.BatchService;
import com.ace.ai.admin.service.TeacherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class ProfileController {
    @Autowired
    AdminProfileService adminProfileService;
    @Autowired
    BatchService batchService;
    @Autowired
    TeacherProfileService teacherProfileService;

    @GetMapping("admin/updateProfileSetUp/")
    public ModelAndView adminProfile(@AuthenticationPrincipal AdminUserDetails userDetails,Model model){
        Admin admin=adminProfileService.findByCode(userDetails.getCode());
             AdminDTO adminDTO= new AdminDTO();
             adminDTO.setName(admin.getName());
             adminDTO.setCode(admin.getCode());

      if(admin.getEmail()!=null){
          adminDTO.setEmail(admin.getEmail());
      }
      if(admin.getPhoto()!=null ){
          model.addAttribute("photoName",admin.getPhoto());

      }
      else{
          model.addAttribute("photoName","default");
      }

       return new ModelAndView("A005","adminDTO",adminDTO);
    }

    @PostMapping("admin/profileUpdate/")
    public ModelAndView adminProfileUpdate( Model model, @ModelAttribute("adminDTO") @Validated AdminDTO adminDTO, BindingResult bs, @AuthenticationPrincipal AdminUserDetails userDetails) throws IOException {
        Admin admin=adminProfileService.findByCode(userDetails.getCode());
        if (bs.hasErrors()) {
            adminDTO.setName(admin.getName());
            adminDTO.setCode(admin.getCode());
            model.addAttribute("photoName",admin.getPhoto());
        }
        else{
            if(adminProfileService.checkPassword(adminDTO.getPassword(),userDetails.getPassword())){
                adminDTO.setCode(admin.getCode());
                model.addAttribute("success","Success");
                String capitalizedName=batchService.capitalize(adminDTO.getName());
                adminDTO.setName(capitalizedName);
                adminProfileService.saveAdmin(adminDTO);
                userDetails.setName(adminDTO.getName());
                if(adminDTO.getPhoto().isEmpty()){
                    if(admin.getPhoto()!=null){
                        userDetails.setPhoto(admin.getPhoto());
                        model.addAttribute("photoName",admin.getPhoto());
                    }
                }
                else {
                    userDetails.setPhoto(adminDTO.getPhoto().getOriginalFilename());
                    model.addAttribute("photoName",adminDTO.getPhoto().getOriginalFilename());
                }

            }
            else{
                adminDTO.setCode(admin.getCode());
                model.addAttribute("photoName",admin.getPhoto());
                model.addAttribute("error","Wrong password!");
            }

        }

        return new ModelAndView("A005","adminDTO",adminDTO);
    }

    @GetMapping("admin/changePasswordSetUp/")
    public ModelAndView adminChangePasswordSetUp(Model model){
        return new ModelAndView("A007","changePassDTO",new ChangePasswordDTO());

    }

    @PostMapping("admin/changePassword/")
    public ModelAndView adminChangePassword(@AuthenticationPrincipal AdminUserDetails userDetails, Model model, @ModelAttribute("changePassDTO") @Validated ChangePasswordDTO changePassDTO, BindingResult br, RedirectAttributes redirect){
           if(br.hasErrors()){

           }
           else {
               if(!adminProfileService.checkPassword(changePassDTO.getOldPassword(),userDetails.getPassword()))
               {
                   model.addAttribute("msg","Wrong Password");
               }
               else if(!changePassDTO.getNewPassword().equals(changePassDTO.getConfirmPassword())){
                     model.addAttribute("error","passwords must be equal");
               }
               else if(adminProfileService.checkPassword(changePassDTO.getNewPassword(),userDetails.getPassword())){
                   model.addAttribute("newPass","You've entered your old password!");
               }
               else{
                   adminProfileService.saveAdminPassword(changePassDTO.getNewPassword(),userDetails.getCode());
                   redirect.addFlashAttribute("success","You have reset your password successfully! You may now login.");
                   return new ModelAndView("redirect:/admin/login","",null);
               }
           }
        return new ModelAndView("A007","changePassDTO",changePassDTO);

    }

    @GetMapping("teacher/updateProfileSetUp/")
    public ModelAndView teacherProfile(@AuthenticationPrincipal TeacherUserDetails userDetails, Model model){
       Teacher teacher=teacherProfileService.findByCode(userDetails.getCode());
       TeacherDTO teacherDTO= new TeacherDTO();
        teacherDTO.setName(teacher.getName());
        teacherDTO.setCode(teacher.getCode());


        if(teacher.getPhoto()!=null){
            model.addAttribute("photoName",teacher.getPhoto());

        }
        else{
            model.addAttribute("photoName","default");
        }

        return new ModelAndView("T006","teacherDTO",teacherDTO);
    }
    @PostMapping("teacher/profileUpdate/")
    public ModelAndView teacherProfileUpdate( Model model, @ModelAttribute("teacherDTO") @Validated TeacherDTO teacherDTO, BindingResult bs, @AuthenticationPrincipal TeacherUserDetails userDetails) throws IOException {
        Teacher teacher=teacherProfileService.findByCode(userDetails.getCode());
        if (bs.hasErrors()) {
            teacherDTO.setName(teacher.getName());
            teacherDTO.setCode(teacher.getCode());
            model.addAttribute("photoName",teacher.getPhoto());
        }
        else{
            if(adminProfileService.checkPassword(teacherDTO.getPassword(),userDetails.getPassword())){
                teacherDTO.setCode(teacher.getCode());
                model.addAttribute("success","Success");
                String capitalizedName=batchService.capitalize(teacherDTO.getName());
                teacherDTO.setName(capitalizedName);
                teacherProfileService.saveTeacher(teacherDTO);
                userDetails.setName(teacherDTO.getName());
                if(teacherDTO.getPhoto().isEmpty()){
                    if(teacher.getPhoto()!=null){
                        userDetails.setPhoto(teacher.getPhoto());
                        model.addAttribute("photoName",teacher.getPhoto());
                    }
                }
                else {
                    userDetails.setPhoto(teacherDTO.getPhoto().getOriginalFilename());
                    model.addAttribute("photoName",teacherDTO.getPhoto().getOriginalFilename());
                }

            }
            else{
                teacherDTO.setCode(teacher.getCode());
                model.addAttribute("photoName",teacher.getPhoto());
                model.addAttribute("error","Wrong password!");
            }

        }

        return new ModelAndView("T006","teacherDTO",teacherDTO);
    }
    @GetMapping("teacher/changePasswordSetUp/")
    public ModelAndView teacherChangePasswordSetUp(Model model){
        return new ModelAndView("T008","changePassDTO",new ChangePasswordDTO());

    }

    @PostMapping("teacher/changePassword/")
    public ModelAndView teacherChangePassword(@AuthenticationPrincipal TeacherUserDetails userDetails, Model model, @ModelAttribute("changePassDTO") @Validated ChangePasswordDTO changePassDTO, BindingResult br, RedirectAttributes redirect){
        if(br.hasErrors()){

        }
        else {
            if(!adminProfileService.checkPassword(changePassDTO.getOldPassword(),userDetails.getPassword()))
            {
                model.addAttribute("msg","Wrong Password");
            }
            else if(!changePassDTO.getNewPassword().equals(changePassDTO.getConfirmPassword())){
                model.addAttribute("error","passwords must be equal");
            }
            else if(adminProfileService.checkPassword(changePassDTO.getNewPassword(),userDetails.getPassword())){
                model.addAttribute("newPass","You've entered your old password!");
            }
            else{
                teacherProfileService.saveTeacherPassword(changePassDTO.getNewPassword(),userDetails.getCode());
                redirect.addFlashAttribute("success","You have reset your password successfully! You may now login.");
                return new ModelAndView("redirect:/teacher/login","",null);
            }
        }
        return new ModelAndView("T008","changePassDTO",changePassDTO);

    }


}
