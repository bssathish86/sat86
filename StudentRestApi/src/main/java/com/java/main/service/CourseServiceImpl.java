package com.java.main.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.main.model.Course;
import com.java.main.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService
{
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course findById(long id)
    {
        final List<Course> courses = findAllCourses();
        if (courses != null && courses.size() > 0)
            for (final Course course : courses)
                if (course.getId() == id)
                    return course;
        return null;
    }

    @Override
    public Course findByName(String name)
    {
        final List<Course> courses = findAllCourses();
        if (courses != null && courses.size() > 0)
            for (final Course course : courses)
                if (course.getName().equalsIgnoreCase(name))
                    return course;
        return null;
    }

    @Override
    public void saveCourse(Course course)
    {
        courseRepository.save(course);
    }

    @Override
    public void updateCourse(Course course)
    {
        courseRepository.saveAndFlush(course);
    }

    @Override
    public void deleteCourseById(long id)
    {
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> findAllCourses()
    {
        return courseRepository.findAll();
    }

    @Override
    public void deleteAllCourses()
    {
        courseRepository.deleteAll();
    }

    @Override
    public boolean isCourseExist(Course course)
    {
        return findByName(course.getName()) != null;
    }

    @Override
    public List<Course> findByStudentId(long id)
    {

        final List<Course> courses = findAllCourses();
        List<Course> courseList = null;

        if (courses != null && courses.size() > 0) {
            courseList = new ArrayList<>();

            for (final Course course : courses)
                if (course.getStudent().getId() == id)
                    courseList.add(course);
        }
        return courseList;
    }

}
