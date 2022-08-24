package com.ace.ai.admin.dtomodel;


import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;

@Data
public class TeacherDTO {
    private int id;
    private String code;
    private String name;
    private String password;
    private String photo;



}
