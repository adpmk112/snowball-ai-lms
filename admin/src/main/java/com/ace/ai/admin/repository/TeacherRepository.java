package com.ace.ai.admin.repository;

import java.util.List;
import com.ace.ai.admin.datamodel.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

  List<Teacher> findByDeleteStatus(boolean deleteStatus);

  Teacher findByCodeAndDeleteStatus(String code, boolean b);

  boolean existsByCode(String code);

  Teacher findByCode(String code);

  List<Teacher> findAllByDeleteStatus(boolean b);

  Teacher findTeacherById(Integer id);

  Teacher findTeacherByCode(String code);

  Teacher findTeacherByName(String teacherName);

  List<Teacher> findByDeleteStatusAndCode(boolean deleteStatus, String code);

}
