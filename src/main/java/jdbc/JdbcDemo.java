package jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

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
