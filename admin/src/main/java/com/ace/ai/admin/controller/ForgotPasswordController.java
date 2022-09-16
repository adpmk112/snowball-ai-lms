package com.ace.ai.admin.controller;

import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.exception.AdminNotFoundException;
import com.ace.ai.admin.service.AdminProfileService;
import com.ace.ai.admin.service.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@Controller
public class ForgotPasswordController {
    @Autowired
    AdminProfileService adminProfileService;

    @GetMapping("/forgot_password")
    public String showPasswordForm(Model model){
        return "A008";
    }

    @PostMapping("/forgot_password")
    public String forgotPassword(HttpServletRequest request, Model model){
       String email=request.getParameter("email");
       String token= RandomString.make(45);
        try {
            adminProfileService.updateResetPassword(token,email);
            //generate reset password link based on the token
            String resetPasswordLink= Utility.getSiteURL(request)+"/reset_password?token="+token;
            //send email
            adminProfileService.sendEmail(email,resetPasswordLink);
            model.addAttribute("message","We have sent a reset password link to your email.");
            }catch (AdminNotFoundException e) {
            model.addAttribute("error", e.getMessage());
        } catch ( UnsupportedEncodingException |MessagingException e) {
               model.addAttribute("error","Error while sending email.");
            }
        return "A008";
    }
    @GetMapping("/reset_password")
    public String showResetPasswordForm(@Param(value="token") String token, Model model){
         Admin admin=adminProfileService.get(token);
         if(admin==null){
             model.addAttribute("message","Invalid Token");

         }
         model.addAttribute("token",token);

        return "A009";

    }
    @PostMapping("/reset_password")
    public String resetPassword(HttpServletRequest request,Model model){
          String token=request.getParameter("token");
          String password=request.getParameter("newPassword");
          Admin admin=adminProfileService.get(token);
        if(admin==null){
            model.addAttribute("message","Invalid Token");
            return "A009";
        }
        else{
            adminProfileService.updatePassword(admin,password);
            model.addAttribute("message","You have successfully changed your password!");
            return "ALGN001";
        }


    }


}
