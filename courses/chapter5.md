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
```java
public class JdbcDemo {
    public static void main(String[] args) {
        var sql = "select * from student";
        try(var conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1/db", "root", "")) {
            if (conn != null) {
                System.out.println("Connected to the database");
            } else {
                System.out.println("Failed to make connection");
            }
            // 1、statement
            var stmt = conn.createStatement();
            var rs = stmt.executeQuery(sql);
            System.out.println("statement ---- ");
            while (rs.next()) {
                var id = rs.getInt("id");
                var name = rs.getString("name");
                System.out.println((new Student(id, name)));
            }

            // 2、prepare statement
            var preparedStatement = conn.prepareStatement(sql);
            var rs2 = preparedStatement.executeQuery();
            System.out.println("prepare statement ---- ");
            while (rs2.next()) {
                var id = rs2.getInt("id");
                var name = rs2.getString("name");
                System.out.println((new Student(id, name)));
            }

            // 3、事务
            conn.setAutoCommit(false);
            var insertPrepare = conn.prepareStatement("insert into student values (3, 'tom')");
            insertPrepare.executeUpdate();
            conn.commit();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n$s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4、HikariCP 连接池
        try(var conn = DataSource.getConnection()) {
            var pst = conn.prepareStatement(sql);
            var rs = pst.executeQuery(sql);
            System.out.println("HikariCP ---- ");
            while (rs.next()) {
                var id = rs.getInt("id");
                var name = rs.getString("name");
                System.out.println((new Student(id, name)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/**
 * HikariDataSource 单例
 */
class DataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    static {
        config.setJdbcUrl("jdbc:mysql://127.0.0.1/db");
        config.setUsername("root");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        ds = new HikariDataSource(config);
    }
    private DataSource() {};
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}

@Data
@AllArgsConstructor
@ToString
class Student {
    Integer id;
    String name;
}

```

