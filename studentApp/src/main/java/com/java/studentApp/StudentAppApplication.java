package com.java.studentApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication(scanBasePackages = {"com.java.studentApp"})
public class StudentAppApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(StudentAppApplication.class, args);
    }

}
