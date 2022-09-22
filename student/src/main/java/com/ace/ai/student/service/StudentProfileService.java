package com.ace.ai.student.service;

import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.dtomodel.StudentDTO;
import com.ace.ai.student.repository.StudentRepository;
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
public class StudentProfileService {
    @Autowired
    StudentRepository studentRepository;
    public boolean checkPassword(String newPassword,String existingPassword){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        if(encoder.matches(newPassword,existingPassword)){
            return true;
        }
        else return false;


    }
    //capitalize the name
    public static String capitalize(String inputString){
        char[] charArray=inputString.toCharArray();
        boolean isSpace=true;
        for(int i=0;i<charArray.length;i++){
            if(Character.isLetter(charArray[i])){
                if(isSpace){
                    charArray[i]=Character.toUpperCase(charArray[i]);
                    isSpace=false;
                }

            }
            else{
                isSpace=true;
            }
        }
        String outputString=String.valueOf(charArray);
        return outputString;
    }

    public Student findByCode(String code){
        return studentRepository.findByCode(code);
    }
    public void saveAdmin(StudentDTO studentDTO) throws IOException {
        Student student=studentRepository.findByCode(studentDTO.getCode());
        if(!studentDTO.getPhoto().isEmpty()){

            Path path = Paths.get("./assets/img/"+student.getCode()+"/"+student.getPhoto());
            if(Files.exists(path)){
                Files.delete(path);
            }
            student.setPhoto(studentDTO.getPhoto().getOriginalFilename());

            String uploadDir="./assets/img/"+student.getCode()+"/";
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                try {
                    Files.createDirectories(uploadPath);
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
            try( InputStream inputStream=studentDTO.getPhoto().getInputStream()){
                Path filePath=uploadPath.resolve(studentDTO.getPhoto().getOriginalFilename());
                System.out.println(filePath.toFile().getAbsolutePath());
                Files.copy(inputStream, filePath , StandardCopyOption.REPLACE_EXISTING);
            }catch (IOException e){
                try {
                    throw new IOException("Could not save upload file: " + studentDTO.getPhoto().getOriginalFilename());
                } catch (IOException e1) {

                    e1.printStackTrace();
                }
            }
        }


        student.setName(studentDTO.getName());

        studentRepository.save(student);

    }

    public void saveStudentPassword(String password,String code){
        Student student=studentRepository.findByCode(code);
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        student.setPassword(encoder.encode(password));
        studentRepository.save(student);
    }
}
