package com.java.studentApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.studentApp.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
