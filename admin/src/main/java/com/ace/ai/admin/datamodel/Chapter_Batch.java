package com.ace.ai.admin.datamodel;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
public class Chapter_Batch implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private String start_date;
    private String end_date;
    private Boolean delete_status;

    @ManyToOne
    @JoinColumn(name="chapter_id")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name="batch_id")
    private Batch batch;


}