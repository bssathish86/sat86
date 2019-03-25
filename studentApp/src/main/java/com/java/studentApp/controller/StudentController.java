package com.java.studentApp.controller;

import java.security.Principal;
import java.util.List;

import javax.transaction.Transactional;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.java.studentApp.exception.CustomException;
import com.java.studentApp.model.Student;
import com.java.studentApp.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController
{

    public static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    @RequestMapping("/user")
    public Principal user(Principal pr)
    {
        return pr;
    }

    @GetMapping("/students")
    public ResponseEntity<List<Student>> listAllStudents(Long id)
    {
        StudentController.logger.info("listAllStudents students details  ", id);
        final List<Student> students = studentService.findAllStudents();

        if (students.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping("/student")
    @Transactional
    public ResponseEntity<?> createStudent(@RequestBody Student student, UriComponentsBuilder ucBuilder)
    {
        StudentController.logger.info("Creating student : {}", student);

        if (studentService.isStudentExist(student)) {
            StudentController.logger.error("Unable to create. A student with name {} already exist", student.getName());
            return new ResponseEntity<>(
                new CustomException("Unable to create. A Student with name " + student.getName() + " already exist."),
                HttpStatus.CONFLICT);
        }

        student.getCourses().forEach(e -> e.setStudent(student));

        studentService.saveStudent(student);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable("id") long id, @RequestBody Student student)
    {
        StudentController.logger.info("Updating student with id {}", id);

        final Student currentStudent = studentService.findById(id);

        if (currentStudent == null) {
            StudentController.logger.error("Unable to update. student with id {} not found.", id);
            return new ResponseEntity<>(new CustomException("Unable to upate. Student with id " + id + " not found."),
                HttpStatus.NOT_FOUND);
        }

        currentStudent.setName(student.getName());
        currentStudent.setClazz(student.getClazz());
        currentStudent.setAddress(student.getAddress());

        studentService.updateStudent(currentStudent);
        return new ResponseEntity<>(currentStudent, HttpStatus.OK);
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable("id") long id)
    {
        StudentController.logger.info("Fetching & Deleting Student with id {}", id);

        final Student student = studentService.findById(id);
        if (student == null) {
            StudentController.logger.error("Unable to delete. Student with id {} not found.", id);
            return new ResponseEntity<>(new CustomException("Unable to delete. Student with id " + id + " not found."),
                HttpStatus.NOT_FOUND);
        }
        studentService.deleteStudentById(id);
        return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/students")
    public ResponseEntity<Student> deleteAllUsers()
    {
        StudentController.logger.info("Deleting All Students");

        studentService.deleteAllStudents();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
