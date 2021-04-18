package com.example.demo.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Klass {

    List<Student> students;

    public void print(){
        System.out.println(this.getStudents());
    }

}
