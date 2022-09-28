package com.ace.ai.admin.service;

import com.ace.ai.admin.datamodel.*;
import com.ace.ai.admin.dtomodel.StudentAttendanceDTO;
import com.ace.ai.admin.dtomodel.StudentDTO;
import com.ace.ai.admin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    ChapterRepository chapterRepository;

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
                   for(Teacher teacher:exceptTeacher){
                       System.out.println(teacher.getName());
                       System.out.println(teacher.getId());
                       System.out.println(teacher.getCode());
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

    public List<Course> findAllCourseByDeleteStatus(){
        return courseRepository.findByDeleteStatus(false);
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

    public void addTeacherByCodeAndBatchId(Integer id,Integer batchId){

        Teacher newTeacher=teacherRepository.findTeacherById(id);
        System.out.println(newTeacher.getName());
        Batch batch=batchRepository.findBatchById(batchId);
        TeacherBatch teacherBatch=new TeacherBatch(false,newTeacher,batch);
        teacherBatchRepository.save(teacherBatch);
    }

    public void saveStudent(ArrayList<StudentDTO> studentList){
        System.out.println(studentList.get(0).toString());
        Integer batchId= studentList.get(0).getBatchId();
        Batch batch=batchRepository.findBatchById(batchId);

        System.out.println(batchId);
        for(StudentDTO student: studentList){
            Student student1=new Student();
               
            student1.setCode(student.getCode().toUpperCase());
            BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
            String encodedPassword=encoder.encode(student.getPassword());
            student1.setPassword(encodedPassword);
            String outputName=BatchService.capitalize(student.getName());
            student1.setName(outputName);

            student1.setBatch(batch);
            studentRepository.save(student1);
        }

    }
    //capitalize the name 
    public static String capitalize(String inputString){
        char[] charArray=inputString.toCharArray();
        boolean isSpace=true;
        for(int i=0;i<charArray.length;i++){
            if(Character.isLetter(charArray[i])){
                if(isSpace){
                    charArray[i]=Character.toUpperCase(charArray[i]);
                    isSpace=false;
                }
                
            }
            else{
                isSpace=true;
            }
        }
        String outputString=String.valueOf(charArray);
        return outputString;
    }
    public List<StudentDTO> findALlStudentByBatchId(Integer batchId){
        List<Student> students=studentRepository.findByDeleteStatus(false);
        List<StudentAttendanceDTO> studentDTOList1=adminDashboardService.getStuAttendanceByBatch(batchId);
        Batch batch=batchRepository.findBatchById(batchId);
        List<StudentDTO> studentDTOList=new ArrayList<>();
        for(Student s:students){
           if(s.getBatch().getId()==batch.getId()) {
               StudentDTO studentDTO=new StudentDTO();
               studentDTO.setId(s.getId());
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

     public Batch findBatchByIdAndName(Integer id,String name){
        return batchRepository.findBatchByIdAndName(id,name);

     }

    public StudentDTO updateStudent(StudentDTO studentDTO){
        Batch batch= batchRepository.findBatchById(studentDTO.getBatchId());
        Student student= studentRepository.findByBatchAndId(batch,studentDTO.getId());
        BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
        String encodedPassword=encoder.encode(student.getPassword());
        student.setPassword(encodedPassword);
        student.setName(studentDTO.getName());
        student.setCode(studentDTO.getCode());
        studentRepository.save(student);
        if(student.getPhoto()!=null) {
            studentDTO.setPhoto(student.getPhoto());
        }
        studentDTO.setId(student.getId());
        return  studentDTO;
    }

    public List<String> findStudentIdsByBatchId(Integer batchId){
        List<String> studentCodesList=new ArrayList<>();
        Batch batch= batchRepository.findBatchById(batchId);
        List<Student> studentList =studentRepository.findByBatch(batch);
        for(Student s:studentList){
           studentCodesList.add(s.getCode());
        }
        return studentCodesList;
    }

    public void removeTeacherFromBatch(String teacherCode,Integer batchId){
          Teacher teacher=teacherRepository.findTeacherByCode(teacherCode);
          Batch batch=batchRepository.findBatchById(batchId);
         TeacherBatch teacherBatch=teacherBatchRepository.findTeacherBatchByBatchAndTeacher(batch,teacher);
         teacherBatchRepository.delete(teacherBatch);

    }
    public StudentDTO findStudentById(Integer id){
       Student student=studentRepository.findStudentById(id);
       StudentDTO studentDTO=new StudentDTO();
        System.out.println("student id is :"+ student.getId());
       studentDTO.setId(student.getId());
       studentDTO.setBatchId(student.getBatch().getId());
      // studentDTO.setAttendance();
        studentDTO.setPhoto(student.getPhoto());
        studentDTO.setCode(student.getCode());
        studentDTO.setName(student.getName());
        studentDTO.setPassword(student.getPassword());
        return studentDTO;
    }

    public void removeStudentFromBatch(Integer id){
        Student student=studentRepository.findStudentById(id);
      student.setDeleteStatus(true);
      studentRepository.save(student);
    }

    public Batch getById(int id){
        return batchRepository.getById(id);
    }

    public List<Chapter> findChapterByCourseId(int courseId) {
        return chapterRepository.findByCourseIdAndDeleteStatus(courseId,0);
    }

    public List<Batch> findBatchesByTeacherCode(String loginUser) {
         Teacher teacher=teacherRepository.findTeacherByCode(loginUser);
         List<TeacherBatch> teacherBatches=(teacher != null)?teacher.getTeacherBatches() : new ArrayList<>();
         List<Batch> batchList=new ArrayList<>();
         for(TeacherBatch tb:teacherBatches){
             if(!tb.isDeleteStatus()){
                 batchList.add(tb.getBatch());
             }

         }
         return batchList;
    }

    public void deleteById(int batchId){
        batchRepository.deleteById(batchId);
    }
}
