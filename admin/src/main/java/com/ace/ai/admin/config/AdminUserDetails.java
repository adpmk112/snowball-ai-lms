package com.ace.ai.admin.config;

import com.ace.ai.admin.datamodel.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AdminUserDetails implements UserDetails {
    private Admin admin;
//    private boolean isActive=true;

    public AdminUserDetails(Admin admin) {
        this.admin = admin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getCode();
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
        return admin.getName();
    }
    public String getPhoto(){
        return admin.getPhoto();
    }
    public String getCode(){
        return admin.getCode();
    }
    public String getEmail(){return admin.getEmail();}

    public void setName(String name){
        this.admin.setName(name);
    }
    public void setPhoto(String photo){
        this.admin.setPhoto(photo);
    }

}

