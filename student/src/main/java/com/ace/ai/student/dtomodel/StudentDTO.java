package com.ace.ai.student.dtomodel;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class StudentDTO {
    private int id;
    private String name;
    private String code;
    private String password;
    private MultipartFile photo;
    private double attendance;
    private Integer batchId;

}
