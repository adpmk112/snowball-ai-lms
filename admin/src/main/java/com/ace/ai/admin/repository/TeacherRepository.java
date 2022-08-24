
package com.ace.ai.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Teacher;



@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Integer>{

    Teacher findByCode(String code);
    
     List<Teacher> findAllByDeleteStatus(boolean b);
      Teacher findTeacherById(Integer id);
   

    
in
}
