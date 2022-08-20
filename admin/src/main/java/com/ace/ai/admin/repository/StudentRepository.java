package com.ace.ai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer> {
    
}
