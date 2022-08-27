package com.ace.ai.admin.dtomodel;


import lombok.Data;

@Data
public class StudentDTO {
    private String name;
    private String id;
    private String password;
    private String photo;
    private String attendance;
    private Integer batchId;

}
