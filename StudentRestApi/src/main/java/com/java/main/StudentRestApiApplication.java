package com.java.main;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableJpaAuditing
@RestController
@RequestMapping("/api")
public class StudentRestApiApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(StudentRestApiApplication.class, args);
    }

    @RequestMapping(value = "/user")
    public Principal user(Principal principal)
    {
        return principal;
    }

}
