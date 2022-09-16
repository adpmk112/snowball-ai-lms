package com.ace.ai.student.config;



import com.ace.ai.student.datamodel.Student;
import com.ace.ai.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Locale;

public class StudentUserDetailsService implements UserDetailsService {
    @Autowired
    private StudentRepository repo;
    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        String toUpperCase=code.toUpperCase();
        Student student=repo.findByCodeAndDeleteStatus(toUpperCase,false);

        if(student==null){
            throw new UsernameNotFoundException("User not found");

        }
        return new StudentUserDetails(student);
    }

}
