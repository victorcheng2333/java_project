package com.example.demo.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class School implements ISchool{
    @Autowired(required = true) //primary
    public Klass class1;

    @Resource(name = "student123")
    Student student123;

    @Override
    public void ding(){
        System.out.println("Class1 have " + this.class1.getStudents().size() + " students and one is " + this.student123);
    }
}
