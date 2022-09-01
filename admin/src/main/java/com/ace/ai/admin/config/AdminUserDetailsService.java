package com.ace.ai.admin.config;

import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepository repo;
    @Override
    public UserDetails loadUserByUsername(String code) throws UsernameNotFoundException {
        Admin admin=repo.findByCode(code);
        if(admin==null){
            throw new UsernameNotFoundException("User not found");

        }
        return new AdminUserDetails(admin);
    }
}
