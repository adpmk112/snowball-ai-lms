package com.ace.ai.admin.dtomodel;

import lombok.Data;

@Data
public class StudentDTO {
    private int id;
    private String name;
    private String code;
    private String password;
    private String photo;
    private String attendance;

}
