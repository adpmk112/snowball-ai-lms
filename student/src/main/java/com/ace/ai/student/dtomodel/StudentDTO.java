package com.ace.ai.student.dtomodel;


import lombok.Data;

import java.util.List;

@Data
public class StudentDTO {
    private int id;
    private String name;
    private String code;
    private String password;
    private String photo;
    private double attendance;
    private Integer batchId;

}
