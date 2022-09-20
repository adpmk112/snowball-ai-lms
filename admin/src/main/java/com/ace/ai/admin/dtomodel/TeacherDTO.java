package com.ace.ai.admin.dtomodel;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private int id;
    private String code;
    @NotEmpty(message = "Name is mandatory!")
    private String name;
    @NotEmpty(message = "Password is mandatory!")
    private String password;
    private MultipartFile photo;

}
