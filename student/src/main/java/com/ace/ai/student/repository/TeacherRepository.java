package com.ace.ai.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Integer> {
    Teacher findByCode(String code);
}
