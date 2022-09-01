package com.ace.ai.admin.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String rawPassword="admin";
        String encodedPassword=encoder.encode(rawPassword);
        System.out.println(encodedPassword);
    }
}
