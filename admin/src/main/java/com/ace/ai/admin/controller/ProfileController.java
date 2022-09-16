package com.ace.ai.admin.controller;

import com.ace.ai.admin.config.AdminUserDetails;
import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.dtomodel.AdminDTO;
import com.ace.ai.admin.dtomodel.ChangePasswordDTO;
import com.ace.ai.admin.service.AdminProfileService;
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

    @GetMapping("admin/updateProfileSetUp/")
    public ModelAndView adminProfile(@AuthenticationPrincipal AdminUserDetails userDetails,Model model){
        Admin admin=adminProfileService.findByCode(userDetails.getCode());
             AdminDTO adminDTO= new AdminDTO();
             adminDTO.setName(admin.getName());
             adminDTO.setCode(admin.getCode());

      if(admin.getEmail()!=null){
          adminDTO.setEmail(admin.getEmail());
      }
      if(admin.getPhoto()!=null){
          model.addAttribute("photoName",admin.getPhoto());

      }
      else{
          model.addAttribute("photoName","default");
      }

       return new ModelAndView("A005","adminDTO",adminDTO);
    }
    @GetMapping("teacher/profile/")
    public String teacherProfile(){

        return "T006";
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
            if(adminProfileService.checkAdminPassword(adminDTO.getPassword(),userDetails.getPassword())){
                adminDTO.setCode(admin.getCode());
                model.addAttribute("success","Success");
                adminProfileService.saveAdmin(adminDTO);
                userDetails.setName(adminDTO.getName());
                if(adminDTO.getPhoto().isEmpty()){
                    userDetails.setPhoto(admin.getPhoto());
                    model.addAttribute("photoName",admin.getPhoto());
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
               if(!adminProfileService.checkAdminPassword(changePassDTO.getOldPassword(),userDetails.getPassword()))
               {
                   model.addAttribute("msg","Wrong Password");
               }
               else if(!changePassDTO.getNewPassword().equals(changePassDTO.getConfirmPassword())){
                     model.addAttribute("error","passwords must be equal");
               }
               else if(adminProfileService.checkAdminPassword(changePassDTO.getNewPassword(),userDetails.getPassword())){
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
}
