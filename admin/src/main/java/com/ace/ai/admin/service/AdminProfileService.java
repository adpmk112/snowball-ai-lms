package com.ace.ai.admin.service;

import com.ace.ai.admin.config.AdminUserDetails;
import com.ace.ai.admin.config.PasswordEncoder;
import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.dtomodel.AdminDTO;
import com.ace.ai.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AdminProfileService {
    @Autowired
    AdminRepository adminRepository;

    public boolean checkAdminPassword(String newPassword,String existingPassword){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if(encoder.matches(newPassword,existingPassword)){
            return true;
        }
        else return false;


    }

    public Admin findByCode(String code){
       return adminRepository.findByCode(code);
    }
    public void saveAdmin(AdminDTO adminDTO) throws IOException {
        Admin admin=adminRepository.findByCode(adminDTO.getCode());
        if(!adminDTO.getPhoto().isEmpty()){

            Path path = Paths.get("./assets/img/admin/"+admin.getPhoto());
            Files.delete(path);
            admin.setPhoto(adminDTO.getPhoto().getOriginalFilename());

            String uploadDir="./assets/img/admin/";
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            try( InputStream inputStream=adminDTO.getPhoto().getInputStream()){
                Path filePath=uploadPath.resolve(adminDTO.getPhoto().getOriginalFilename());
                System.out.println(filePath.toFile().getAbsolutePath());
                Files.copy(inputStream, filePath , StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                try {
                    throw new IOException("Could not save upload file: " + adminDTO.getPhoto().getOriginalFilename());
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            }
        }

        System.out.println("admin email : "+ admin.getEmail());
        admin.setEmail(adminDTO.getEmail());
        admin.setName(adminDTO.getName());

        //String fileName= StringUtils.cleanPath(admin.getPhoto().getOriginalFilename());
        //admin.setPhoto(adminDTO.);

        adminRepository.save(admin);

    }

    public void saveAdminPassword(String password,String code){
        Admin admin=adminRepository.findByCode(code);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(password));
        adminRepository.save(admin);
    }
}
