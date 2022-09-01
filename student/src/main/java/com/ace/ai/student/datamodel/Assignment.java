package com.ace.ai.student.datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Assignment implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String name;
    private String filePath;
    private String assignmentChapterName;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;
    private String branch;

    @OneToMany(mappedBy = "assignment")
    private List<StudentAssignmentMark> studentAssignmentMarks = new ArrayList<>();
}
