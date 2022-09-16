package com.ace.ai.student.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.student.datamodel.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{
    List<Student> findByCodeAndPassword(String stuCode,String password);
    Student findByCode(String code);
    Student findByCodeAndDeleteStatus(String code,Boolean b);

}
