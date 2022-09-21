package com.ace.ai.admin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ace.ai.admin.datamodel.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Integer> {
    List<Course> findByDeleteStatus(Boolean deleteStatus);
    Course findCourseById(Integer id);
}
