package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.StudentExamMark;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.dtomodel.TeacherDashboardChartDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardDTO;
import com.ace.ai.admin.dtomodel.TeacherDashboardExamDTO;
import com.ace.ai.admin.repository.AttendanceRepository;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.StudentExamMarkRepository;
import com.ace.ai.admin.repository.StudentRepository;
import com.ace.ai.admin.repository.TeacherBatchRepository;
import com.ace.ai.admin.repository.TeacherDashboardRepository;
import com.ace.ai.admin.repository.TeacherRepository;

@Service
public class TeacherDashboardService {

    @Autowired
    TeacherDashboardRepository teacherDashboardRepository;
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    TeacherBatchRepository teacherBatchRepository;
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    StudentExamMarkRepository studentExamMarkRepository;
  

    public List<Batch> findBatchesByTeacherCode(String teacherCode) {
        Teacher teacher = teacherRepository.findTeacherByCode(teacherCode);
        List<TeacherBatch> teacherBatches = teacher.getTeacherBatches();
        List<Batch> batchList = new ArrayList<>();
        for (TeacherBatch tb : teacherBatches) {
            if (!tb.isDeleteStatus()) {
                batchList.add(tb.getBatch());
            }
        }
        return batchList;
    }

    

    public List<TeacherDashboardDTO> getBatchNameAndStuCountByTeacherCode(String teacherCode){
        Teacher teacher = teacherRepository.findByCode(teacherCode);
        List<TeacherBatch> teacherBatchList = teacherBatchRepository.findByTeacherIdAndDeleteStatus(teacher.getId(), false);
        List<TeacherDashboardDTO> teacherDashboardDTOList = new ArrayList<>();
        for(TeacherBatch teacherBatch : teacherBatchList){
            TeacherDashboardDTO teacherDashboardDTO = new TeacherDashboardDTO();
            teacherDashboardDTO.setBatchId(teacherBatch.getBatch().getId());
            teacherDashboardDTO.setBatchName(teacherBatch.getBatch().getName());
            int studentCount = studentRepository.findByDeleteStatusAndBatchId(false, teacherBatch.getBatch().getId()).size();
            teacherDashboardDTO.setStudentCount(studentCount);
            teacherDashboardDTOList.add(teacherDashboardDTO);
        }
        return teacherDashboardDTOList;
    }

   public List<TeacherDashboardChartDTO> findStudentByBatchId(int batchId){
       List<Student> studentList = studentRepository.findByDeleteStatusAndBatchId(false,batchId);
       List<TeacherDashboardChartDTO> teacherDashboardChartDTOlist = new ArrayList<>();
       for(Student student : studentList){
        TeacherDashboardChartDTO teacherDashboardChartDTO = new TeacherDashboardChartDTO();
            int totalDays = attendanceRepository.findByStudentId(student.getId()).size();
            int attendDays = attendanceRepository.findByAttendAndStudentId("present", student.getId()).size();
            int attendPercentage;
            if(totalDays == 0){
                attendPercentage = 0;
            }
            else{
                attendPercentage = (totalDays * 100) % attendDays;
            }
            teacherDashboardChartDTO.setBatchId(batchId);
            teacherDashboardChartDTO.setStudentName(student.getName());
            teacherDashboardChartDTO.setAttendance(attendPercentage);
            teacherDashboardChartDTOlist.add(teacherDashboardChartDTO);
       }
       return teacherDashboardChartDTOlist;
    }

    // List<TeacherDashboardExamDTO> getStuNameAndExamMarkByBatchId(int batchId){
            
    // }

    public List<TeacherDashboardDTO> getStuNameAndExamMarksByTeacherCode(String teacherCode){
        Teacher teacher = teacherRepository.findByCode(teacherCode);
        List<TeacherBatch> teacherBatchList = teacherBatchRepository.findByTeacherIdAndDeleteStatus(teacher.getId(), false);
        List<TeacherDashboardDTO> teacherDashboardDTOList = new ArrayList<>();
        for(TeacherBatch teacherBatch : teacherBatchList){
            TeacherDashboardExamDTO teacherDashboardExamDTO = new TeacherDashboardExamDTO();
            List<Student> studentList = studentRepository.findByDeleteStatusAndBatchId(false, teacherBatch.getBatch().getId());
            for(Student student : studentList){
                List<StudentExamMark> studentExamMarksList = studentExamMarkRepository.findByStudentIdAndDeleteStatus(student.getId(), false);
            }
        }
    }
}
