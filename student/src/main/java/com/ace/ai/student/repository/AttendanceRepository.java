package com.ace.ai.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ace.ai.student.datamodel.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer>{
    Attendance findByStudent_IdAndClassroom_Id(int studentId, int classroomId);
    
}
