package com.java.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.main.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>
{

}
