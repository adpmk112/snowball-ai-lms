package com.ace.ai.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
@Order(2)
public class TeacherSecurityConfig {
    @Bean
    public UserDetailsService teacherDetailsService(){
        return new TeacherUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder teacherPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider2(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(teacherDetailsService());
        authenticationProvider.setPasswordEncoder(teacherPasswordEncoder());
        return authenticationProvider;
    }



    @Bean
    public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception{
        http.authenticationProvider(authenticationProvider2());

        // http.authorizeRequests().antMatchers("/").permitAll();

        http.antMatcher("/teacher/**")
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/teacher/login")
                .usernameParameter("code")
                .loginProcessingUrl("/teacher/login")
                .defaultSuccessUrl("/teacher/home")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/teacher/logout")
                .logoutSuccessUrl("/")
                .and()
                .rememberMe().key("remember-me").userDetailsService(teacherDetailsService()).tokenValiditySeconds(2628000).rememberMeCookieName("remember-me");

        return http.build();
    }
}
