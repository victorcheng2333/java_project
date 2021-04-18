package com.example.demo;

import com.example.demo.config.MyConfig;
import com.example.demo.pojo.School;
import com.example.demo.pojo.Student;
import com.example.demo.pojo2.Student2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）
 */
public class SpringLoadBeanDemo {
    public static void main(String[] args) {
        // 1、在XML中装配bean
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Student student = (Student) context.getBean("student123");
        System.out.println(student.toString());

        // 2、xml 中配置 component-scan 类上配置 @Componnt 注解
        School school = context.getBean(School.class);
        school.ding();

        // 3、通过配置类 @Configuration
        AnnotationConfigApplicationContext context2 = new AnnotationConfigApplicationContext(MyConfig.class);
        Student2 student2 = context.getBean(Student2.class);
        System.out.println(student2.toString());
    }
}
