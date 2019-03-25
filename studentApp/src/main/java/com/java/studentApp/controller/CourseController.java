package com.java.studentApp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.java.studentApp.exception.CustomException;
import com.java.studentApp.model.Course;
import com.java.studentApp.model.Student;
import com.java.studentApp.service.CourseService;

@RestController
public class CourseController
{
    public static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    CourseService courseService;

    @GetMapping("/courses")
    public ResponseEntity<List<Course>> listAllCourses(Long id)
    {
        CourseController.logger.info("listAll course details  ", id);
        final List<Course> courses = courseService.findAllCourses();

        if (courses.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @GetMapping("/course/student/{id}")
    public ResponseEntity<List<Course>> getAllCoursesByStudentId(@PathVariable(value = "student_id") Long id)
    {
        CourseController.logger.info("listAll student course details  ", id);
        final List<Course> courses = courseService.findByStudentId(id);

        if (courses.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }

    @PostMapping("/course")
    public ResponseEntity<?> createCourse(@RequestBody Course course, UriComponentsBuilder ucBuilder)
    {
        CourseController.logger.info("Creating course : {}", course);

        if (courseService.isCourseExist(course)) {
            CourseController.logger.error("Unable to create. A course with name {} already exist", course.getName());
            return new ResponseEntity<>(
                new CustomException("Unable to create. A course with name " + course.getName() + " already exist."),
                HttpStatus.CONFLICT);
        }
        courseService.saveCourse(course);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/course/{id}").buildAndExpand(course.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable("id") long id, @RequestBody Course course)
    {
        CourseController.logger.info("Updating course with id {}", id);

        final Course currentCourse = courseService.findById(id);

        if (currentCourse == null) {
            CourseController.logger.error("Unable to update. course with id {} not found.", id);
            return new ResponseEntity<>(new CustomException("Unable to upate. course with id " + id + " not found."),
                HttpStatus.NOT_FOUND);
        }

        currentCourse.setName(course.getName());
        currentCourse.setGrade(course.getGrade());
        currentCourse.setStudent(course.getStudent());

        courseService.updateCourse(currentCourse);
        return new ResponseEntity<>(currentCourse, HttpStatus.OK);
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") long id)
    {
        CourseController.logger.info("Fetching & Deleting course with id {}", id);

        final Course course = courseService.findById(id);
        if (course == null) {
            CourseController.logger.error("Unable to delete. Student with id {} not found.", id);
            return new ResponseEntity<>(new CustomException("Unable to delete. Student with id " + id + " not found."),
                HttpStatus.NOT_FOUND);
        }
        courseService.deleteCourseById(id);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/courses")
    public ResponseEntity<Course> deleteAllCourses()
    {
        CourseController.logger.info("Deleting All Courses");

        courseService.deleteAllCourses();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
