package com.ace.ai.student.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.repository.StudentRepository;

@Service
public class StudentLoginService {

    @Autowired
    StudentRepository studentRepository;




    public boolean studentAccExistCheck(String code,String password){
        List<Student> studentList = studentRepository.findByCodeAndPassword(code, password);
        boolean loginStatus;
        //check acc exist or not
        if(studentList.size()==0){
            loginStatus = false;
        }else{
            loginStatus = true;
        }
        

        return loginStatus;

    }

    public Student studnetLoginFindBatch(String code,String password){
        List<Student> studentList = studentRepository.findByCodeAndPassword(code, password);
        Student availableStudent = new Student();
        for(Student student : studentList){
            if(student.getBatch().isDeleteStatus()==false){
                        availableStudent = student;
                    }
        }
        return availableStudent;
    }

}
