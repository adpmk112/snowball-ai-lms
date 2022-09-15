package com.ace.ai.admin.dtomodel;

import java.time.LocalDate;

import lombok.Data;

@Data
public class BatchCustomChapterDTO {
    private int id;
    private String name; //chapter table
    private String status;//calculation
    private LocalDate start_date; // chapter batch table
    private LocalDate end_date;
}
