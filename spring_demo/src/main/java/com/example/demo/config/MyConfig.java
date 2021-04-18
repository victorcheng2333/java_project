package com.example.demo.config;

import com.example.demo.pojo2.Student2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "com.example.demo.pojo2")
public class MyConfig {
    @Bean("getStudent2")
    public Student2 getStudent() {
        return new Student2(18, "name_18");
    }
}
