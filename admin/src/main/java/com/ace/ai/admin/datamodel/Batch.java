package com.ace.ai.admin.datamodel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Batch {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany(mappedBy = "batch")
    private List<Chapter_Batch> chapter_batches = new ArrayList<>();

    private boolean delete_status;
    private Date created_date;
}
