package com.ace.ai.admin.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Teacher;



@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Integer>{

    List<Teacher> findByDeleteStatus(Boolean deleteStatus);

    boolean existsByCode(String code);

    Teacher findByCode(String code);   
}
