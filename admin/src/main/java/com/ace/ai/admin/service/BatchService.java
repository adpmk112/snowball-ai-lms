package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.*;
import com.ace.ai.admin.dtomodel.StudentAttendanceDTO;
import com.ace.ai.admin.dtomodel.StudentDTO;
import com.ace.ai.admin.repository.*;
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
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    AdminDashboardService adminDashboardService;

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
        List<Teacher> allTeacherList=teacherRepository.findAllByDeleteStatus(false);
        List<TeacherBatch> teacherBatches= teacherBatchRepository.findByBatchId(batchId);
        List<Teacher> exceptTeacher=teacherRepository.findAllByDeleteStatus(false);
                   for(Teacher t: allTeacherList) {
                       for (TeacherBatch tb : teacherBatches){
                           if(t.getName().equals(tb.getTeacher().getName())){
                                   exceptTeacher.remove(t);

                           }
                       }
                   }
       return exceptTeacher;
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

    public void addTeacherByCodeAndBatchId(String code,Integer batchId){

        Teacher newTeacher=teacherRepository.findByCode(code);
        System.out.println(newTeacher.getName());
        Batch batch=batchRepository.findBatchById(batchId);
        TeacherBatch teacherBatch=new TeacherBatch(false,newTeacher,batch);
        teacherBatchRepository.save(teacherBatch);
    }

    public void saveStudent(ArrayList<StudentDTO> studentList){
        Integer batchId= studentList.get(0).getBatchId();
        String code=studentList.get(0).getCode();
        System.out.println("Code is: "+code);
        Batch batch=batchRepository.findBatchById(batchId);

        System.out.println(batchId);
        for(StudentDTO student: studentList){
            Student student1=new Student();
            student1.setCode(student.getCode());
            student1.setPassword(student.getPassword());
            student1.setName(student.getName());

            student1.setBatch(batch);
            studentRepository.save(student1);
        }

    }
    public List<StudentDTO> findALlStudentByBatchId(Integer batchId){
        List<Student> students=studentRepository.findByDeleteStatus(false);
        List<StudentAttendanceDTO> studentDTOList1=adminDashboardService.getStuAttendanceByBatch(batchId);
        Batch batch=batchRepository.findBatchById(batchId);
        List<StudentDTO> studentDTOList=new ArrayList<>();
        for(Student s:students){
           if(s.getBatch().getId()==batch.getId()) {
               StudentDTO studentDTO=new StudentDTO();
               studentDTO.setCode(s.getCode());
               studentDTO.setBatchId(s.getBatch().getId());
               studentDTO.setName(s.getName());
               studentDTO.setPhoto(s.getPhoto());
               studentDTO.setPassword(s.getPassword());
               studentDTOList.add(studentDTO);
            }
        }

        for(StudentAttendanceDTO studentAttendanceDTO:studentDTOList1){
            for(StudentDTO s:studentDTOList){
                if(studentAttendanceDTO.getStuId()==s.getId()){
                    s.setAttendance(studentAttendanceDTO.getAttendance());
                }
            }

        }
        return studentDTOList;
    }

    public StudentDTO findStudentByBatchIdAndStudentId(Integer batchId,String code){
        List<Student> studentList= studentRepository.findByDeleteStatusAndBatchId(false,batchId);
        StudentDTO studentDTO=new StudentDTO();
        for(Student s: studentList){
            if(s.getCode().equals(code))
            {

               studentDTO.setPassword(s.getPassword());
               studentDTO.setName(s.getName());
               studentDTO.setCode(s.getCode());
               studentDTO.setBatchId(s.getBatch().getId());
               studentDTO.setPhoto(s.getPhoto());
               studentDTO.setId(s.getId());

            }
        }
        return studentDTO;
    }

    public StudentDTO updateStudent(StudentDTO studentDTO){
                      Batch batch= batchRepository.findBatchById(studentDTO.getBatchId());
       Student student= studentRepository.findByBatchAndCode(batch,studentDTO.getCode());
       student.setPassword(studentDTO.getPassword());
       student.setName(studentDTO.getName());
       student.setCode(studentDTO.getCode());
       studentRepository.save(student);
       studentDTO.setPhoto(student.getPhoto());
       studentDTO.setId(student.getId());
       return  studentDTO;
    }
}
