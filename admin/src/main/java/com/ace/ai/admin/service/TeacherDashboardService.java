package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.dtomodel.TeacherDashboardDTO;
import com.ace.ai.admin.repository.BatchRepository;
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
}
