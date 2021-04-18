##### 2、写代码实现 Spring Bean 的装配，方式越多越好（XML、Annotation 都可以）, 提交到 GitHub
```java
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
```
##### 8、给前面课程提供的 Student/Klass/School 实现自动配置和 Starter
starter demo： https://github.com/victorcheng2333/java_project/blob/master/starter-demo/src/main/java/com/example/starterdemo/HelloAutoConfiguration.java

#### 10、研究一下 JDBC 接口和数据库连接池


