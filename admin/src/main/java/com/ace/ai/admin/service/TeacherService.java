package com.ace.ai.admin.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.repository.TeacherRepository;


@Service
public class TeacherService {
    @Autowired
    public TeacherRepository teacherRepository;

  public List<Teacher> findAllTeacher(){
    return teacherRepository.findAll();
  }

  public Teacher getId(Integer  id){
    return teacherRepository.findById(id).get();
  }
   
  public void saveTeacher(Teacher teacher){
  teacherRepository.save(teacher);
  }

  public boolean existsByCode(String code) {
		return teacherRepository.existsByCode(code);
	}

  public Teacher getCode(String code){
    return teacherRepository.findByCode(code);
  }

}
