package com.java.studentApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.studentApp.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>
{

}
