package com.ace.ai.student.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@Configuration
public class StudentSecurityConfig {
    @Bean
    public UserDetailsService studentDetailsService(){
        return new StudentUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder studentPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(studentDetailsService());
        authenticationProvider.setPasswordEncoder(studentPasswordEncoder());
        return authenticationProvider;
    }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authenticationProvider(authenticationProvider());

        // http.authorizeRequests().antMatchers("/").permitAll();

        http.antMatcher("/student/**")
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin()
                .loginPage("/student/login")
                .usernameParameter("code")
                .loginProcessingUrl("/student/login")
                .defaultSuccessUrl("/student/home")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/student/logout")
                .logoutSuccessUrl("/")
                .and()
                .rememberMe().key("remember-me").userDetailsService(studentDetailsService()).tokenValiditySeconds(2628000).rememberMeCookieName("remember-me");

        return http.build();
    }
}
