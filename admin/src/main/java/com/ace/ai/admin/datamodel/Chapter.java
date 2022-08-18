package com.ace.ai.admin.datamodel;

import javax.persistence.*;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Chapter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private boolean delete_status;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "chapter")
    private List<Chapter_Batch> chapter_Batches = new ArrayList<>();

    @OneToMany(mappedBy = "chapter")
    private List<Chapter_File> chapter_Files = new ArrayList<>();
}
