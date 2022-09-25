package com.ace.ai.student.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ai.student.datamodel.Batch;
import com.ace.ai.student.datamodel.Teacher;
import com.ace.ai.student.datamodel.TeacherBatch;

import java.util.List;

public interface TeacherBatchRepository extends JpaRepository<TeacherBatch, Integer> {

    List<TeacherBatch> findByBatchId(int id);
    TeacherBatch findTeacherBatchByBatchAndTeacher(Batch batch, Teacher teacher);
    List<TeacherBatch> findByBatchIdAndTeacherIdNot(Integer batchId,Integer teacherId);
    List<TeacherBatch> findByTeacherIdAndDeleteStatus(int teacherId,boolean deleteStatus);
}
