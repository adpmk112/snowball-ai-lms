package com.ace.ai.admin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ace.ai.admin.repository.StudentAssignmentMarkRepository;

@Service
public class StudentAssignmentMarkService {
    @Autowired
    StudentAssignmentMarkRepository studentAssignmentMarkRepository;
}
