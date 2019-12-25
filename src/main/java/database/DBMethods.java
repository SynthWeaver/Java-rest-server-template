package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBMethods {
    public PreparedStatement getPreparedStatementFromQuery(String query) throws SQLException {
        Connection connection = DBConnection.connection();
        return connection.prepareStatement(query);
    }
}
