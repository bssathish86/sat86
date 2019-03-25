package com.java.main.service;

import java.util.List;

import com.java.main.model.Course;

public interface CourseService
{
    Course findById(long id);

    List<Course> findByStudentId(long id);

    Course findByName(String name);

    void saveCourse(Course course);

    void updateCourse(Course course);

    void deleteCourseById(long id);

    List<Course> findAllCourses();

    void deleteAllCourses();

    boolean isCourseExist(Course course);

}
