package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository  extends JpaRepository<Admin,Integer> {

    Admin findByCode(String code);
    Admin findByResetPasswordToken(String token);
    Admin findByEmail(String email);
}
