package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.Batch;
import com.ace.ai.admin.datamodel.Course;
import com.ace.ai.admin.datamodel.Teacher;
import com.ace.ai.admin.datamodel.TeacherBatch;
import com.ace.ai.admin.repository.BatchRepository;
import com.ace.ai.admin.repository.CourseRepository;
import com.ace.ai.admin.repository.TeacherBatchRepository;
import com.ace.ai.admin.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {
    @Autowired
    BatchRepository batchRepository;
    @Autowired
    TeacherRepository teacherRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    TeacherBatchRepository teacherBatchRepository;

    public Course findCourseById(Integer id){
        return courseRepository.findCourseById(id);
    }
    public Batch findBatchByName(String name){
        return batchRepository.findBatchByName(name);
    }
    public List<Batch> findAll(){
        return batchRepository.findAll();
    }

    public List<Teacher> findALlTeacherByDeleteStatus(boolean b){
        return teacherRepository.findAllByDeleteStatus(false);
    }
    public List<Teacher> findALlTeacherForAllBatchExcept(Integer batchId){
       List<TeacherBatch> teacherBatches= teacherBatchRepository.findByBatchId(batchId);
       List<Teacher> teacherList=new ArrayList<>();
       List<Teacher> allTeacherList=teacherRepository.findAllByDeleteStatus(false);
       for(TeacherBatch tb:teacherBatches){
           if(!tb.getTeacher().isDeleteStatus()){
               teacherList.add(tb.getTeacher());
           }
       }
       for(Teacher t:teacherList){
           for(Teacher t1:allTeacherList){
               if(t1.getId()==t.getId())
                   allTeacherList.remove(t1);
           }
       }
       return allTeacherList;
    }

    public List<Teacher> findALlTeacherByBatchId(Integer batchId){
        List<TeacherBatch> teacherBatches= teacherBatchRepository.findByBatchId(batchId);
        List<Teacher> teacherList=new ArrayList<>();

        for(TeacherBatch tb:teacherBatches){
            if(!tb.getTeacher().isDeleteStatus()){
                teacherList.add(tb.getTeacher());
            }
        }


        return teacherList;
    }

    public List<Course> findAllCourse(){
        return courseRepository.findAll();
    }

    public void saveTeacherBatch(Integer teacherId,Integer batchId){
        Teacher teacher=teacherRepository.findTeacherById(teacherId);
        Batch batch=batchRepository.findBatchById(batchId);
        TeacherBatch teacherBatch=new TeacherBatch(false,teacher,batch);
         teacherBatchRepository.save(teacherBatch);
    }

    public void saveBatch(Batch batch){
        batchRepository.save(batch);
    }

    public Batch findLastBatch(){
       return batchRepository.findLastBatch();
    }
    public Batch findBatchById(Integer id){
        return batchRepository.findBatchById(id);
    }

    public Integer getTotalBatches(){
        return batchRepository.getTotalBatches();
    }
}
