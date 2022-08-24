package com.ace.ai.admin.repository;

import com.ace.ai.admin.datamodel.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    Course findCourseById(Integer id);
}
