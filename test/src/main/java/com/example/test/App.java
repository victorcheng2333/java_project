package com.example.test;

import com.example.starterdemo.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class App {

    @Autowired
    private Hello hello;

    @RequestMapping("/")
    public String index() {
        return hello.sayHello();
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}