package com.ace.ai.admin.service;

import com.ace.ai.admin.config.AdminUserDetails;
import com.ace.ai.admin.config.PasswordEncoder;
import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.dtomodel.AdminDTO;
import com.ace.ai.admin.exception.AdminNotFoundException;
import com.ace.ai.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class AdminProfileService {
    @Autowired
    AdminRepository adminRepository;
    @Autowired
    JavaMailSender javaMailSender;

    public boolean checkPassword(String newPassword,String existingPassword){
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
            if(Files.exists(path)){
            Files.delete(path);
            }
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

        adminRepository.save(admin);

    }

    public void saveAdminPassword(String password,String code){
        Admin admin=adminRepository.findByCode(code);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        admin.setPassword(encoder.encode(password));
        adminRepository.save(admin);
    }

    public void updateResetPassword(String token,String email) throws AdminNotFoundException {
        Admin admin=adminRepository.findByEmail(email);
        if(admin!=null){
            admin.setResetPasswordToken(token);
            adminRepository.save(admin);
        }
        else{
            throw new AdminNotFoundException("We will send an email if the email exists. ");
        }
    }

    public Admin get(String resetPasswordToken){
        return adminRepository.findByResetPasswordToken(resetPasswordToken);
    }

    public void updatePassword(Admin admin,String newPassword){
        BCryptPasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String encodedPassword=passwordEncoder.encode(newPassword);
        admin.setPassword(encodedPassword);
        admin.setResetPasswordToken(null);
        adminRepository.save(admin);
    }
    public void sendEmail(String email, String resetPasswordLink) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message= javaMailSender.createMimeMessage();
        MimeMessageHelper helper=new MimeMessageHelper(message);
        helper.setFrom("sawmonhan71293@gmail.com","Ace Inspiration Support");
        helper.setTo(email);
        String subject="Here's the link to reset your password.";
        String content="<p> Hello,</p>"
                +"<p>You have requested to reset your password. </p>"
                +"<p>Click the link below to change your password.</p>"
                +"<p><b><a href=\""+resetPasswordLink+"\">Change my password.</a></b></p>"
                +"<p>Ignore this email if you do remember your password, or you have not made the request.</p>";
        helper.setSubject(subject);
        helper.setText(content,true);
        javaMailSender.send(message);


    }

}
