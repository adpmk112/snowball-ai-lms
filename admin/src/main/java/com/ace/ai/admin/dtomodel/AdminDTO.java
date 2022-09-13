package com.ace.ai.admin.dtomodel;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Data
public class AdminDTO {
    private int id;
    @NotEmpty(message = "Name is mandatory!")
    private String name;
    private MultipartFile photo;
    private String code;
    @NotEmpty(message = "Password is mandatory!")
    private String password;
    @NotEmpty(message = "Email is mandatory!")
    @Email
    private String email;
}
