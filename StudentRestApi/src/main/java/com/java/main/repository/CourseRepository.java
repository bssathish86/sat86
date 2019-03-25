package com.java.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.main.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>
{

}
