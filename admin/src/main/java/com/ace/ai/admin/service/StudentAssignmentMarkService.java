package com.ace.ai.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.datamodel.Student;
import com.ace.ai.admin.datamodel.StudentAssignmentMark;
import com.ace.ai.admin.dtomodel.AssignmentMarkDTO;
import com.ace.ai.admin.dtomodel.StudentIdMarkFilePathDTO;
import com.ace.ai.admin.repository.AssignmentRepository;
import com.ace.ai.admin.repository.StudentAssignmentMarkRepository;

@Service
public class StudentAssignmentMarkService {
    @Autowired
    StudentAssignmentMarkRepository studentAssignmentMarkRepository;
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired AttendanceService attendanceService;
    //get all Data
    public List<AssignmentMarkDTO> getAssignmentMarkDTOList(int batchId){
        List<Assignment> assignments = assignmentRepository.findByDeleteStatusAndBatchId(false, batchId);
        List<AssignmentMarkDTO> assignmentMarkDTOList = new ArrayList<>();
        for(Assignment assignment: assignments){
            AssignmentMarkDTO assignmentMarkDTO = this.getAssignmentMarkDTO(assignment, batchId);
            if(assignmentMarkDTO.getStudentData().size() >0 ){
                assignmentMarkDTOList.add(assignmentMarkDTO);
            }
        }
        return assignmentMarkDTOList;
    }
    
    public AssignmentMarkDTO getAssignmentMarkDTO(Assignment assignment,int batchId){
        List<Student> allStudents = attendanceService.getAllStudentByDeleteStatus(batchId);
        List<StudentIdMarkFilePathDTO> studentDataList = new ArrayList<>();
        for(Student student : allStudents){
            StudentAssignmentMark studentAssignmentMark = studentAssignmentMarkRepository.findByAssignment_IdAndStudent_Id(assignment.getId(), student.getId());
            StudentIdMarkFilePathDTO studentData = new StudentIdMarkFilePathDTO();
            studentData.setStudentId(student.getId());
            if(studentAssignmentMark != null){
                studentData.setMark(studentAssignmentMark.getStudentMark());
                studentData.setFilePath(studentAssignmentMark.getUploadedFile());
            }else{
                studentData.setMark(0);
            }
            studentDataList.add(studentData);
        }  
        return new AssignmentMarkDTO(assignment,studentDataList,batchId, assignment.getId()); 
    }

    public void save(StudentAssignmentMark studentAssignmentMark){
        studentAssignmentMarkRepository.save(studentAssignmentMark);
    }

    public StudentAssignmentMark getByAssignmentIdAndStudentAssignmentMark(int assignmentId, int studentId){
        return studentAssignmentMarkRepository.findByAssignment_IdAndStudent_Id(assignmentId, studentId);
    }

}
