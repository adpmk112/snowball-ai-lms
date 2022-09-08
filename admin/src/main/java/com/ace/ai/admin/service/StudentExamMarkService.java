package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.StudentExamMark;
import com.ace.ai.admin.dtomodel.ExamMarkDTO;
import com.ace.ai.admin.dtomodel.StudentIdMarkFilePathDTO;
import com.ace.ai.admin.repository.StudentExamMarkRepository;

@Service
public class StudentExamMarkService {
    @Autowired
    StudentExamMarkRepository studentExamMarkRepository;
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    ExamScheduleService examScheduleService;    

    //get all Data and if data doesn't present insert with default values
    public List<ExamMarkDTO> getExamMarkDTOList(int batchId){
        List<Student> allStudents =  attendanceService.getAllStudentByDeleteStatus(batchId);
        List<ExamForm> finishedExams =  examScheduleService.getFinishedExam(batchId);
        List<ExamMarkDTO> examMarkDTOList = new ArrayList<>();
        for(ExamForm finishedExam : finishedExams){
            List<StudentExamMark> studentExamMark = studentExamMarkRepository.findByExamForm_Id(finishedExam.getId());
            
            if(studentExamMark.size() < 1){    //If it doesn't exit insert as default data
                for(Student student: allStudents){
                    StudentExamMark studentExamMarkToInsertDefault = new StudentExamMark(0, "", true, student, finishedExam);
                    studentExamMarkRepository.save(studentExamMarkToInsertDefault);
                }
            } 
            //get Data
            ExamMarkDTO examMarkDTO = this.getExamMarkDTO(finishedExam);
            examMarkDTOList.add(examMarkDTO);      
        }       
        return examMarkDTOList;
    }

    //get student data and exam form form studentexamMark Table
    public ExamMarkDTO getExamMarkDTO(ExamForm examForm){
        List<StudentExamMark> studentExamMarkList = studentExamMarkRepository.findByExamForm_IdOrderByStudent_IdAsc(examForm.getId());
        ExamForm exam = studentExamMarkList.get(0).getExamForm();
        List<StudentIdMarkFilePathDTO> studentDataList = new ArrayList<>();
        for(StudentExamMark studentExamMark : studentExamMarkList){
            StudentIdMarkFilePathDTO studentData = new StudentIdMarkFilePathDTO(studentExamMark.getStudent().getId(),studentExamMark.getStudentMark(), studentExamMark.getUploadedFile());
            studentDataList.add(studentData); 
        }
        return new ExamMarkDTO(exam, studentDataList,exam.getId());
    }

    public StudentExamMark getByExamIdAndStudentId(int examId, int studentId){
        return studentExamMarkRepository.findByExamForm_IdAndStudent_Id(examId, studentId);
    }

    //save
    public void save(StudentExamMark studentExamMark){
        studentExamMarkRepository.save(studentExamMark);
    }

}
