package com.ace.ai.admin.config;


import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.repository.TeacherRepository;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class TeacherUserDetailsService implements UserDetailsService {
    @Autowired
    private TeacherRepository repo;
    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        Teacher teacher=repo.findByCode(code);
        if(teacher==null || teacher.isDeleteStatus()){
            throw new UsernameNotFoundException("User not found");

        }
        return new TeacherUserDetails(teacher);
    }

}
