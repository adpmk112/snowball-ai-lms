package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.ExamForm;
import com.ace.ai.admin.repository.ExamFormRepository;

@Service
public class ExamFormService {
    
    @Autowired ExamFormRepository examFormRepo;

    public void saveExam(ExamForm exam){
        try{
            examFormRepo.save(exam);
            System.out.println("Exam form saved.");
        }catch(Exception ex){
            System.out.println("ExamForm save Error.");
        }        
    }

    public ExamForm findById(int id){
        return examFormRepo.getById(id);
    }

    public int findCurrentId(){
        return examFormRepo.findCurrentId();
    }
}
