package database;

import Vars;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private static Connection con = null;

    private static MysqlDataSource dataSource = new MysqlDataSource();

    public static Connection connection() throws SQLException {
        dataSource.setURL(Vars.URL);
        dataSource.setUser(Vars.USERNAME);
        dataSource.setPassword(Vars.PASSWORD);

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