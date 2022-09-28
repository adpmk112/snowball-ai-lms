package com.ace.ai.admin.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.ace.ai.admin.datamodel.Attendance;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Classroom;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.StudentAttendanceDTO;
import com.ace.ai.admin.repository.AttendanceRepository;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.CourseRepository;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.repository.TeacherRepository;
import com.ace.ai.admin.service.AdminDashboardService;


@SpringBootTest
public class AdminDashboardServiceTest {

    @Mock
    BatchRepository batchRepository;
    @Mock
    StudentRepository studentRepository;
    @Mock
    AttendanceRepository attendanceRepository;
    @Mock
    CourseRepository courseRepository;
    @Mock
    TeacherRepository teacherRepository;
    @InjectMocks
    AdminDashboardService adminDashboardService;

    

    @Test
    public void getCourseListTest(){
       List<Course> courseList = new ArrayList<>();
       Course course1 = new Course(1,"Java","28/10/2022",false);
       Course course2 = new Course(2,"PHP","29/10/2022",false);
       courseList.add(course1);
       courseList.add(course2);
       when(courseRepository.findByDeleteStatus(false)).thenReturn(courseList);
       List<Course> courseListService = adminDashboardService.getCourseList(false);
       assertEquals(2, courseListService.size());
       verify(courseRepository,times(1)).findByDeleteStatus(false);

    }

    @Test
    public void getBatchListTest(){
        int courseId = 1;
        Course course = new Course();
        course.setId(courseId);
        List<Batch> batchList = new ArrayList<>();
        Batch batch1 = new Batch(1,"OJT",false,"28/5/2022",course);
        Batch batch2 = new Batch(2,"PFC",false,"12/5/2022",course);
        batchList.add(batch1);
        batchList.add(batch2);
        when(batchRepository.findByDeleteStatus(false)).thenReturn(batchList);
        List<Batch> batchServiceList = adminDashboardService.getBatchList(false);
        assertEquals(2, batchServiceList.size());
        verify(batchRepository,times(1)).findByDeleteStatus(false);
    }
    
    @Test
    public void getTeacherListTest(){
        List<Teacher> teacherList = new ArrayList<>();
        Teacher teacher1 = new Teacher(1,"TEC-01","Win","doggo.jpg","12@56",false);
        Teacher teacher2 = new Teacher(2,"TEC-02","Khaing","404.gif","345",false);
        teacherList.add(teacher1);
        teacherList.add(teacher2);
        when(teacherRepository.findByDeleteStatus(false)).thenReturn(teacherList);
        List<Teacher> teacherListService = adminDashboardService.getTeacherList(false);
        assertEquals(2, teacherListService.size());
        verify(teacherRepository,times(1)).findByDeleteStatus(false);
    }

    
    @Test
    public void getStuAttendanceByBatchTest(){
        int batchId = 1;
        Batch batch = new Batch();
        batch.setId(batchId);
        List<Student> studentList = new ArrayList<>();
        Student student1 = new Student(1,"Mg","doggo.jpg","STU001","234",false,batch); 
        Student student2 = new Student(2,"Kyaw","404.gif","STU002","546",false,batch);
        studentList.add(student1);
        studentList.add(student2);
        int stud = 1;
        Student student = new Student();
        student.setId(stud);
        int classId = 1;
        Classroom classroom = new Classroom();
        classroom.setId(classId);
        Attendance attendance = new Attendance();
        attendance.setId(1);
        attendance.setAttend("persent");        
        attendance.setStudent(student);
        List<StudentAttendanceDTO> studentAttendanceDTOList = new ArrayList<>();
        for(Student studList : studentList){
            StudentAttendanceDTO studentAttendanceDTO = new StudentAttendanceDTO();
            int totalDays = attendanceRepository.findByStudentId(student.getId()).size();
            int attendDays = attendanceRepository.findByAttendAndStudentId("present", student.getId()).size();
            int attendPercentage;
            if (totalDays == 0) {
                attendPercentage = 0;
            } else {
                attendPercentage = (attendDays * 100) / totalDays;
            }
            studentAttendanceDTO.setBatchId(batchId);
            studentAttendanceDTO.setAttendance(1);
        }
        

        
    }
}
