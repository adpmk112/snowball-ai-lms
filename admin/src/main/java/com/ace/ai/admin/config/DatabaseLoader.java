package com.ace.ai.admin.config;

import com.ace.ai.admin.repository.AdminRepository;
import com.ace.ai.admin.repository.TeacherRepository;

public class DatabaseLoader {
    private AdminRepository adminRepo;
    private TeacherRepository teacherRepo;

    public DatabaseLoader(AdminRepository adminRepository, TeacherRepository teacherRepository) {
        this.adminRepo = adminRepository;
        this.teacherRepo = teacherRepository;
    }

}
