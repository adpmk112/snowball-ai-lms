package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.StudentRepository;
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
    
    public List<Batch> findBatchesByTeacherCode(String teacherName) {
        Teacher teacher=teacherRepository.findTeacherByCode(teacherName);
        List<TeacherBatch> teacherBatches=teacher.getTeacherBatches();
        List<Batch> batchList=new ArrayList<>();
        for(TeacherBatch tb:teacherBatches){
            if(!tb.isDeleteStatus()){
                batchList.add(tb.getBatch());
            }

        }
        return batchList;
   }  


    
}
