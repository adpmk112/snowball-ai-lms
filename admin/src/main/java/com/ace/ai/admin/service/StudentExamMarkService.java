package com.ace.ai.admin.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ace.ai.admin.datamodel.BatchExamForm;
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
    BatchExamFormService batchExamFormService;    

    //get all Data 
    public List<ExamMarkDTO> getExamMarkDTOList(int batchId){        
        List<BatchExamForm> batchExamFormsByBatchId =  batchExamFormService.findByBatch_Id(batchId);//find by batchId and examform delete status and bef_deletestatus
        List<ExamMarkDTO> examMarkDTOList = new ArrayList<>();   
        //Current Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedString = now.format(formatter);
        LocalDateTime formattedNow = LocalDateTime.parse(formattedString, dtf);
        
        for(BatchExamForm batchExamForm : batchExamFormsByBatchId){  
            //filters that are only scheduled and start date is after current
            if(batchExamForm.getStartDate()!=null && !batchExamForm.getStartDate().isBlank() && batchExamForm.getEndDate()!=null && !batchExamForm.getEndDate().isBlank()
                && formattedNow.isAfter( LocalDateTime.parse( batchExamForm.getEndDate().replace("T"," "), dtf))) { 
                ExamMarkDTO examMarkDTO = this.getExamMarkDTO(batchExamForm, batchId);
                if(examMarkDTO.getStudentData().size() > 0){ //add to list only if student list is find
                    examMarkDTOList.add(examMarkDTO);
                }                  
            }         
        }       
        return examMarkDTOList;
    }

    public ExamMarkDTO getExamMarkDTO(BatchExamForm batchExamForm, int batchId){
        List<Student> allStudents =  attendanceService.getAllStudentByDeleteStatus(batchId);//students that are ordered by student Id
        ExamForm exam = batchExamForm.getExamForm();
        int examId = exam.getId();
        List<StudentIdMarkFilePathDTO> studentDataList = new ArrayList<>();
        //get student data
        for(Student student: allStudents){
            StudentExamMark studentExamMark = studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(batchExamForm.getId(), student.getId());
            StudentIdMarkFilePathDTO studentData = new StudentIdMarkFilePathDTO();
            studentData.setStudentId(student.getId());
            if(studentExamMark != null){//need to check students is not answer
                studentData.setMark(studentExamMark.getStudentMark());
                studentData.setFilePath(studentExamMark.getUploadedFile());
            }else{
                studentData.setMark(0);
            }
            studentDataList.add(studentData); 
        }
        return new ExamMarkDTO(exam, studentDataList, examId, batchId);
    }
    
    public StudentExamMark getByBatchExamFormIdAndStudentId(int batchExamFormId, int studentId){
        return studentExamMarkRepository.findByBatchExamForm_IdAndStudent_Id(batchExamFormId, studentId);
    }

    //save
    public void save(StudentExamMark studentExamMark){
        studentExamMarkRepository.save(studentExamMark);
    }

}
