package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.AdminDTO;
import com.ace.ai.admin.dtomodel.TeacherDTO;
import com.ace.ai.admin.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class TeacherProfileService {
    @Autowired
    TeacherRepository teacherRepository;

    public Teacher findByCode(String code){
        return teacherRepository.findByCode(code);
    }
    public void saveTeacher(TeacherDTO teacherDTO) throws IOException {
   Teacher teacher=teacherRepository.findByCode(teacherDTO.getCode());
        if(!teacherDTO.getPhoto().isEmpty()){

        Path path = Paths.get("./assets/img/"+teacherDTO.getCode()+"/"+teacher.getPhoto());
        if(Files.exists(path)){
            Files.delete(path);
        }
        teacher.setPhoto(teacherDTO.getPhoto().getOriginalFilename());

        String uploadDir="./assets/img/"+teacherDTO.getCode()+"/";
        Path uploadPath = Paths.get(uploadDir);
        if(!Files.exists(uploadPath)){
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        try( InputStream inputStream=teacherDTO.getPhoto().getInputStream()){
            Path filePath=uploadPath.resolve(teacherDTO.getPhoto().getOriginalFilename());
            System.out.println(filePath.toFile().getAbsolutePath());
            Files.copy(inputStream, filePath , StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            try {
                throw new IOException("Could not save upload file: " + teacherDTO.getPhoto().getOriginalFilename());
            } catch (IOException e1) {

                e1.printStackTrace();
            }
        }
    }
        teacher.setName(teacherDTO.getName());
       teacherRepository.save(teacher);

}
    public void saveTeacherPassword(String password,String code){
        Teacher teacher=teacherRepository.findByCode(code);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        teacher.setPassword(encoder.encode(password));
        teacherRepository.save(teacher);
    }

}
