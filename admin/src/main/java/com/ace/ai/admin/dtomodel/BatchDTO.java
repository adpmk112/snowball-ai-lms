package com.ace.ai.admin.dtomodel;

import java.util.List;

import com.ace.ai.admin.datamodel.Teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BatchDTO {
    @NotBlank
    @Size(min=5)
    private String name;
    private int courseId;
    private boolean delete_status;
    private String created_date;
    @NotEmpty.List({})
    private List<Teacher> teacherList;
    private int batchId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isDelete_status() {
        return delete_status;
    }

    public void setDelete_status(boolean delete_status) {
        this.delete_status = delete_status;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public List<Teacher> getTeacherId() {
        return teacherList;
    }

    public void setTeacherId(List<Teacher> teacherId) {
        this.teacherList = teacherId;
    }

    public int getBatchId() {
        return batchId;
    }

    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }
}
