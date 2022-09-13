package com.ace.ai.admin.controller;

import com.ace.ai.admin.config.AdminUserDetails;
import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.dtomodel.AdminDTO;
import com.ace.ai.admin.service.AdminProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/")
public class Profile {
    @Autowired
    AdminProfileService adminProfileService;

    @GetMapping("admin/updateProfile/")
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

       model.addAttribute("editType","update");
       return new ModelAndView("A005","adminDTO",adminDTO);
    }
    @GetMapping("teacher/profile/")
    public String teacherProfile(){

        return "T006";
    }

    @PostMapping("admin/profileUpdate/")
    public ModelAndView adminProfileUpdate(HttpSession session, Model model, @ModelAttribute("adminDTO") @Validated AdminDTO adminDTO, BindingResult bs, @AuthenticationPrincipal AdminUserDetails userDetails) throws IOException {
        Admin admin=adminProfileService.findByCode(userDetails.getCode());
        if (bs.hasErrors()) {
            adminDTO.setName(admin.getName());
            adminDTO.setCode(admin.getCode());
        }
        else{
            if(adminProfileService.checkAdminPassword(adminDTO.getPassword(),userDetails.getPassword())){
                adminDTO.setCode(admin.getCode());
                model.addAttribute("success","Success");
                session.setAttribute("photoName",adminDTO.getPhoto());
                adminProfileService.saveAdmin(adminDTO);
                model.addAttribute("photoName",adminDTO.getPhoto().getOriginalFilename());
            }
            else{
                model.addAttribute("error","Wrong password!");
            }

        }
        model.addAttribute("editType","update");
        return new ModelAndView("A005","adminDTO",adminDTO);
    }

    @GetMapping("admin/changepassword/")
    public ModelAndView adminChangePassword(Model model, @ModelAttribute("adminDTO") @Validated Admin admin, BindingResult bs,@AuthenticationPrincipal AdminUserDetails userDetails){

        admin.setCode(userDetails.getCode());
        admin.setName(userDetails.getName());
        if(userDetails.getEmail()!=null){
            admin.setEmail(userDetails.getEmail());
        }
        if(userDetails.getPhoto()!=null){
            admin.setPhoto(userDetails.getPhoto());
        }

        model.addAttribute("editType","change");
        return new ModelAndView("A005","admin",admin);

    }
}
