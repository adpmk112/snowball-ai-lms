package com.ace.ai.admin.datamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import lombok.Data;

@Entity
@Data
public class Reply implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Lob
    @Column(length = 8129)
    private String text;
    private String dateTime;
    private String commenterCode;
    @Column(columnDefinition = "tinyint(1) default 1")
    private boolean notification;
    @Column(columnDefinition = "tinyint(1) default 0")
    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
