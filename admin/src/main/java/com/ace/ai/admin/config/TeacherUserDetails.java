package com.ace.ai.admin.config;


import com.ace.ai.admin.datamodel.Teacher;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;

public class TeacherUserDetails implements UserDetails {
    private Teacher teacher;

    public TeacherUserDetails(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return teacher.getPassword();
    }

    @Override
    public String getUsername() {
        return teacher.getCode();
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
        return teacher.getName();
    }
    public String getPhoto(){
        return teacher.getPhoto();
    }
    public String getCode(){
        return teacher.getCode();
    }
    public void setName(String name){
        this.teacher.setName(name);
    }
    public void setPhoto(String photo){
        this.teacher.setPhoto(photo);
    }
}
