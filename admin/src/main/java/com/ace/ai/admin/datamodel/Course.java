package com.ace.ai.admin.datamodel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String name;
    private Date created_date;
    private Boolean delete_status;

    @OneToMany(mappedBy = "course")
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "course")
    private List<Batch> batches = new ArrayList<>();


}
