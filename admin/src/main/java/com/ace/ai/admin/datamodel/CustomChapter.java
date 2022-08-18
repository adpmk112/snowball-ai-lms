package com.ace.ai.admin.datamodel;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class CustomChapter {
    
    @Id
    private int id;
    private String name;
    private String startDate;
    private String endDate;
    private Boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @OneToMany(mappedBy = "customChapter")
    private List<CustomChapterFile> customChapterFiles = new ArrayList<>();
}
