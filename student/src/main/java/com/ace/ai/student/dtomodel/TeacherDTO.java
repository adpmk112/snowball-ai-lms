package com.ace.ai.student.dtomodel;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private int id;
    private String code;
    private String name;
    private String password;
    private MultipartFile photo;

}
