package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
    public List<Attendance> findAllByClassroomId(Integer classroomId);
    List<Attendance> findByStudentId(int stuId);
    List<Attendance> findByAttendAndStudentId(String attend,int stuId);
    Attendance findByStudentIdAndClassroomId(int studentId, int classroomId);
}
