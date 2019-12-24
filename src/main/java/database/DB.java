package database;

import model.User;
import objects.Password;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DB {
    public PreparedStatement getPreparedStatementFromQuery(String query) throws SQLException {
        Connection connection = DBConnection.connection();
        return connection.prepareStatement(query);
    }

    //
    // MAIN QUERIES
    //

    public void createUser(User user) throws SQLException {
        String query = "INSERT INTO user (email, password) VALUES (?, ?)";

        String hashedPassword = null;
        try {
            hashedPassword = Password.getSaltedHash(user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        PreparedStatement preparedStatement = this.getPreparedStatementFromQuery(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, hashedPassword);

        preparedStatement.executeUpdate();
    }

    public User readUser(String email) throws SQLException {
        String query = "SELECT * FROM user WHERE email = ?";
        PreparedStatement preparedStatement = this.getPreparedStatementFromQuery(query);

        preparedStatement.setString(1, email);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next()){
            return new User(resultSet);
        }else {
            throw new RuntimeException("Resultset has no data");
        }
    }

    public List<User> readUsers() throws SQLException {
        String query = "SELECT * FROM user";
        PreparedStatement preparedStatement = this.getPreparedStatementFromQuery(query);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            users.add(new User(resultSet));
        }

        return users;
    }

    public void updateUser(String email, User user) throws SQLException {
        String query = "UPDATE user SET email = ?, password = ? WHERE email = ?";//todo: check password before update
        PreparedStatement preparedStatement = this.getPreparedStatementFromQuery(query);

        String hashedPassword = null;
        try {
            hashedPassword = Password.getSaltedHash(user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, hashedPassword);
        preparedStatement.setString(3, email);
        preparedStatement.executeUpdate();
    }

    public void deleteUser(String email) throws SQLException {//todo: check password before delete
        String query = "DELETE FROM user WHERE email = ?";
        PreparedStatement preparedStatement = this.getPreparedStatementFromQuery(query);

        preparedStatement.setString(1, email);
        preparedStatement.executeUpdate();
    }

    public boolean login(User inputUser) throws Exception {
        if(inputUser.hasEmptyFields()){
            return false;
        }

        User databaseUser = this.readUser(inputUser.getEmail());
        return Password.check(inputUser.getPassword(), databaseUser.getPassword());
    }
}
