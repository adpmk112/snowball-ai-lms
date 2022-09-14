package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.datamodel.Assignment;
import com.ace.ai.admin.repository.AssignmentRepository;

@Service
public class AssignmentService {
    @Autowired
    AssignmentRepository assignmentRepository;

    public void saveAssignment(Assignment assignment){
        assignmentRepository.save(assignment);
    }
}
