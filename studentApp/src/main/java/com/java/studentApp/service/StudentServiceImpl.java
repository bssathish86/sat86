package com.java.studentApp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.studentApp.model.Student;
import com.java.studentApp.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService
{

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Student findById(long id)
    {
        List<Student> students = findAllStudents();
        if (students != null && students.size() > 0)
            for (Student student : students)
                if (student.getId() == id)
                    return student;
        return null;
    }

    @Override
    public Student findByName(String name)
    {
        List<Student> students = findAllStudents();
        if (students != null && students.size() > 0)
            for (Student student : students)
                if (student.getName().equalsIgnoreCase(name))
                    return student;
        return null;
    }

    @Override
    public void saveStudent(Student student)
    {
        studentRepository.save(student);
    }

    @Override
    public void updateStudent(Student student)
    {
        studentRepository.saveAndFlush(student);
    }

    @Override
    public void deleteStudentById(long id)
    {
        studentRepository.deleteById(id);
    }

    @Override
    public List<Student> findAllStudents()
    {
        return studentRepository.findAll();
    }

    @Override
    public void deleteAllStudents()
    {
        studentRepository.deleteAll();
    }

    @Override
    public boolean isStudentExist(Student student)
    {
        return findByName(student.getName()) != null;
    }

}
