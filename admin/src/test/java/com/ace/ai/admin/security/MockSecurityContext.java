package com.ace.ai.admin.security;

import com.ace.ai.admin.controller.BatchController;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.service.BatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@WebMvcTest(BatchController.class)
@WithMockUser(username = "admin")
public class MockSecurityContext implements SecurityContext {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private Authentication authentication;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    BatchService batchService;
    @MockBean
    private Principal principal;

    public MockSecurityContext(Authentication authentication) {
        this.authentication = authentication;
    }
    @Override
    public Authentication getAuthentication() {
        return this.authentication;
    }

    @Override
    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
    @Test
    public void testGoToBatch() throws Exception {

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Course course = new Course("Java", "09//25/2022", false);
        Course course1 = new Course("C#", "09/25/2022", false);
        List<Batch> batchList = new ArrayList<>();
        batchList.add(new Batch(1, "OJT Batch 4", false, "09/25/2022", course));
        batchList.add(new Batch(2, "OJT Batch 5", false, "09/25/2022", course1));
        when(batchService.findAll()).thenReturn(batchList);
        String url = "/admin/batch/";
        mockMvc.perform(get(url)).andExpect(status().isOk());

    }
}
