package com.ace.ai.admin.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args){
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String rawPassword="admin";
        String rawPassword1="student";
        String encodedPassword=encoder.encode(rawPassword);
        String encodedPassword1=encoder.encode(rawPassword1);
        System.out.println(encodedPassword);
        System.out.println("teacher password "+encodedPassword1);

    }
}
