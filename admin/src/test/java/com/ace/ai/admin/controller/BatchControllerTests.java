package com.ace.ai.admin.controller;

import com.ace.ai.admin.config.AdminUserDetails;
import com.ace.ai.admin.datamodel.Admin;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.repository.BatchExamFormRepository;
import com.ace.ai.admin.repository.ChapterBatchRepository;
import com.ace.ai.admin.service.*;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(BatchController.class)
@WithMockUser(username = "admin")
public class BatchControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    ChapterViewService chapterViewService;
    @MockBean
    BatchService batchService;
    @MockBean
    BatchExamFormService examScheduleService;
    @MockBean
    AttendanceService attendanceService;
    @MockBean
    ExamFormService examFormService;
    @MockBean
    ClassRoomService classRoomService;
    @MockBean
    TeacherBatchService teacherBatchService;
    @MockBean
    ChapterBatchRepository chapterBatchRepository;
    @MockBean
    AssignmentService assignmentService;
    @MockBean
    StudentAssignmentMarkService studentAssignmentMarkService;
    @MockBean
    StudentExamMarkService studentExamMarkService;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private Principal principal;


    @Test
    @WithAnonymousUser
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
