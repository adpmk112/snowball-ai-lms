package com.ace.ai.admin.datamodel;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class Chapter_Batch implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private Date start_date;
    private Date end_date;
    private int delete_status;

    @ManyToOne
    @JoinColumn(name="chapter_id")
    private Chapter chapter;

    @ManyToOne
    @JoinColumn(name="batch_id")
    private Batch batch;
}