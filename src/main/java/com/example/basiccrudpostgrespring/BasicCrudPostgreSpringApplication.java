package com.example.basiccrudpostgrespring;

import com.example.basiccrudpostgrespring.dao.DAO;
import com.example.basiccrudpostgrespring.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;


@SpringBootApplication
public class BasicCrudPostgreSpringApplication {

    private static DAO<Course> dao;

    public BasicCrudPostgreSpringApplication(DAO<Course> dao){
        this.dao = dao;
    }

    public static void main(String[] args) {
        SpringApplication.run(BasicCrudPostgreSpringApplication.class, args);

    }

}
