package com.ace.ai.admin.datamodel;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Chapter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    private boolean delete_status;
    @OneToMany(mappedBy = "chapter")
    private List<Chapter_Batch> chapter_batches = new ArrayList<>();


}
