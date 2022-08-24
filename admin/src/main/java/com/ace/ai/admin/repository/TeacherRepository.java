package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
    List<Teacher> findAllByDeleteStatus(boolean b);
      Teacher findTeacherById(Integer id);
}
