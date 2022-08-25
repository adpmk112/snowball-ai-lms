package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;

public interface TeacherBatchRepository extends JpaRepository<TeacherBatch, Integer> {

    List<TeacherBatch> findByBatchId(int id);
}
