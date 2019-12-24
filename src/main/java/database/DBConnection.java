package database;

import com.mysql.cj.jdbc.MysqlDataSource;
import controller.Variables;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static Connection con = null;

    private static MysqlDataSource dataSource = new MysqlDataSource();

    public static Connection connection() throws SQLException {
        dataSource.setURL(Variables.URL);
        dataSource.setUser(Variables.USERNAME);
        dataSource.setPassword(Variables.PASSWORD);

        if (con == null) {
            con = dataSource.getConnection();
        }

        if(con.isClosed()){
            dataSource = new MysqlDataSource();
            con = dataSource.getConnection();
        }
        return con;
    }
}