package com.ace.ai.student.datamodel;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class ChapterBatch implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String startDate;
    private String endDate;
    @Column(columnDefinition = "tinyint(1) default 0")
    private int deleteStatus;

    @ManyToOne
    @JoinColumn(name="chapter_id")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name="batch_id")
    private Batch batch;
}