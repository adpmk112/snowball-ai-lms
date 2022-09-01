package com.ace.ai.admin.controller;

import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class UserGeneralController {

    @Autowired
    AdminRepository adminRepository;


    public String processRegistration(Admin admin){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encodedPassword=encoder.encode(admin.getPassword());
               admin.setPassword(encodedPassword);
               adminRepository.save(admin);
               return "register success";

    }


}
