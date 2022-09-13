package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherBatchRepository extends JpaRepository<TeacherBatch, Integer> {

    List<TeacherBatch> findByBatchId(int id);
    TeacherBatch findTeacherBatchByBatchAndTeacher(Batch batch, Teacher teacher);
    List<TeacherBatch> findByBatchIdAndTeacherIdNot(Integer batchId,Integer teacherId);
    List<TeacherBatch> findByTeacherIdAndDeleteStatus(int teacherId,boolean deleteStatus);
}
