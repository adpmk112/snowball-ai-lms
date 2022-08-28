package com.ace.ai.admin.dtomodel;


import com.ace.ai.admin.datamodel.Attendance;
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
