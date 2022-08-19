package com.ace.ai.admin.datamodel;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Reply implements Serializable{
    
    @Id
    private int id;
    private String text;
    private String dateTime;
    private String commenterCode;
    private boolean notification;
    private boolean deleteStatus;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
