package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class AdminRepositoryTests {

    @Autowired
    private AdminRepository repo;
    @Autowired
    private TestEntityManager entityManager;
    @Test
    public void testCreateAdmin(){
         Admin admin=new Admin();
         admin.setCode("adm001");
         admin.setPassword("admin");
         Admin savedAdmin= repo.save(admin);
         Admin existAdmin=entityManager.find(Admin.class,savedAdmin.getId());
         assertEquals(existAdmin.getCode(),admin.getCode());




    }
    @Test
    public void testFindAdminByCode(){
        String code="adm001";
        Admin admin=repo.findByCode(code);
        assertNotNull(admin);
    }

}
