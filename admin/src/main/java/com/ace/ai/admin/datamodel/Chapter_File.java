package com.ace.ai.admin.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Chapter_File {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private String file_type;
    private String file_path;
    private Boolean delete_status;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
}
