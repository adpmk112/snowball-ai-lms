package com.ace.ai.admin.dtomodel;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO {
    private String id;
    private String code;
    private String name;
    private String password;
    private MultipartFile photo;


}
