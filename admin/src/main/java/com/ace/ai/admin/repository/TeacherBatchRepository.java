package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeacherBatchRepository extends JpaRepository<TeacherBatch, Integer> {

    List<TeacherBatch> findByBatchId(int id);
    TeacherBatch findTeacherBatchByBatchAndTeacher(Batch batch, Teacher teacher);

    public void deleteByBatchId(int id);
}
