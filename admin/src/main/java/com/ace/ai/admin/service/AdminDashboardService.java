
package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.dtomodel.StudentAttendanceDTO;
import com.ace.ai.admin.repository.AttendanceRepository;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.CourseRepository;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.repository.TeacherRepository;

@Service
public class AdminDashboardService {
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TeacherRepository teacherRepository;

    public List<Course> getCourseList(boolean fale) {
        return courseRepository.findByDeleteStatus(false);
    }

    public List<Batch> getBatchList(boolean fale) {
        return batchRepository.findByDeleteStatus(false);
    }

    public List<Teacher> getTeacherList(boolean fale) {
        return teacherRepository.findByDeleteStatus(false);
    }

    public List<StudentAttendanceDTO> getStuAttendanceByBatch(int batchId) {

        // this is student's all attendance list
        List<StudentAttendanceDTO> studentAttendanceDTOList = new ArrayList<>();

        List<Student> studentList = studentRepository.findByDeleteStatusAndBatchId(false, batchId);
        for (Student student : studentList) {
            // this is student one attendance
            StudentAttendanceDTO studentAttendanceDTO = new StudentAttendanceDTO();

            int totalDays = attendanceRepository.findByStudentId(student.getId()).size();
            int attendDays = attendanceRepository.findByAttendAndStudentId("present", student.getId()).size();
            System.out.println(attendDays);

            int attendPercentage;
            if (totalDays == 0) {
                attendPercentage = 0;
            } else {
                attendPercentage = (attendDays * 100) / totalDays;
            }

            studentAttendanceDTO.setBatchId(batchId);
            studentAttendanceDTO.setStuId(student.getId());
            studentAttendanceDTO.setStuName(student.getName());
            studentAttendanceDTO.setAttendance(attendPercentage);
            // add one attandance to attandance list with student name
            studentAttendanceDTOList.add(studentAttendanceDTO);
        }
        return studentAttendanceDTOList;
    }

    

}
