package com.ace.ai.admin.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.repository.TeacherRepository;
import com.ace.ai.admin.service.TeacherService;


@SpringBootTest
public class TeacherServiceTest {

    @Mock
    TeacherRepository teacherRepository;
    
    @InjectMocks
    TeacherService teacherService;

    @Test
    public void findAllTeacherTest(){
      List<Teacher> teacherList = new ArrayList<>();
      Teacher teacher1 = new Teacher(1,"TEC-01","Win","doggo.jpg","12@56",false);
      Teacher teacher2 = new Teacher(2,"TEC-02","Khaing","404.gif","345",false);
      teacherList.add(teacher1);
      teacherList.add(teacher2);
      when(teacherRepository.findAll()).thenReturn(teacherList);
      List<Teacher> teacherListService = teacherService.findAllTeacher();
      assertEquals(2, teacherListService.size());
      verify(teacherRepository,times(1)).findAll();
    }

    @Test
    public void getIdTest(){
        Teacher teacher = new Teacher(1,"TEC-01","Win","doggo.jpg","12@56",false);
        when(teacherRepository.findById(1)).thenReturn(Optional.ofNullable(teacher));
        Teacher teacherServiceTest = teacherService.getId(1);
        assertEquals(1, teacherServiceTest.getId());
        assertEquals("TEC-01", teacherServiceTest.getCode());
        assertEquals("Win", teacherServiceTest.getName());
        assertEquals("doggo.jpg", teacherServiceTest.getPhoto());
        verify(teacherRepository,times(1)).findById(1);
        
    }

    @Test
    public  void saveTeacherTest(){
        Teacher teacher = new Teacher(1,"TEC-01","Win","doggo.jpg","12@56",false);
        teacherService.saveTeacher(teacher);
        verify(teacherRepository,times(1)).save(teacher);
    }

    @Test
    public void existsByCodeTest(){
        Boolean check=true;
        when(teacherRepository.existsByCode("TEC-01")).thenReturn(check);
        ;
        assertTrue(teacherService.existsByCode("TEC-01"));
        assertFalse(teacherService.existsByCode("TEC_02"));
        verify(teacherRepository,times(1)).existsByCode("TEC-01");
    }

    @Test
    public void getCodeTest(){
        Teacher teacher = new Teacher(1,"TEC-01","Win","doggo.jpg","12@56",false);
        when(teacherRepository.findByCode("TEC-01")).thenReturn(teacher);
        Teacher teacherServiceTest = teacherService.getCode("TEC-01");
        assertEquals(1, teacherServiceTest.getId());
        assertEquals("TEC-01", teacherServiceTest.getCode());
        assertEquals("Win", teacherServiceTest.getName());
        assertEquals("doggo.jpg", teacherServiceTest.getPhoto());
        verify(teacherRepository,times(1)).findByCode("TEC-01");

    }

   
    
}
