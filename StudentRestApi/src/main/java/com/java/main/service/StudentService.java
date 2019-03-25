package com.java.main.service;

import java.util.List;

import com.java.main.model.Student;

public interface StudentService
{

    Student findById(long id);

    Student findByName(String name);

    void saveStudent(Student student);

    void updateStudent(Student student);

    void deleteStudentById(long id);

    List<Student> findAllStudents();

    void deleteAllStudents();

    boolean isStudentExist(Student student);

}
