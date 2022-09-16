package com.ace.ai.student.config;



import com.ace.ai.student.datamodel.Student;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class StudentUserDetails implements UserDetails {
    private Student student;

    public StudentUserDetails(Student student) {
        this.student = student;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return student.getPassword();
    }

    @Override
    public String getUsername() {
        return student.getCode();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getName(){
        return student.getName();
    }
    public int getId(){
        return student.getId();
    }
    public String getPhoto(){
        return student.getPhoto();
    }
    public String getCode(){
        return student.getCode();
    }
    public void setName(String name){
        this.student.setName(name);
    }
    public void setPhoto(String photo){
        this.student.setPhoto(photo);
    }
}
