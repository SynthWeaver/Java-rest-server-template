package database;

import model.User;
import objects.Password;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    public PreparedStatement getPreparedStatementFromQuery(String query) throws SQLException {
        Connection connection = DBConnection.connection();
        return connection.prepareStatement(query);
    }

    //
    // MAIN QUERIES
    //

    public ResponseEntity<Object> createUser(User user) throws SQLException {
        String query = "INSERT INTO user (email, password) VALUES (?, ?)";

        String hashedPassword = null;
        try {
            hashedPassword = Password.getSaltedHash(user.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        PreparedStatement preparedStatement = this.getPreparedStatementFromQuery(query);
        preparedStatement.setString(1, user.getEmail());
        preparedStatement.setString(2, hashedPassword);

        preparedStatement.executeUpdate();
        return ResponseEntity.status(HttpStatus.OK).body(null);
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

    public ResponseEntity<Object> updateUser(User oldUser, User newUser) throws Exception {
        //validate user
        if(!login(oldUser))  {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        String query = "UPDATE user SET email = ?, password = ? WHERE email = ?";
        PreparedStatement preparedStatement = this.getPreparedStatementFromQuery(query);

        String hashedPassword = null;
        try {
            hashedPassword = Password.getSaltedHash(newUser.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        preparedStatement.setString(1, newUser.getEmail());
        preparedStatement.setString(2, hashedPassword);
        preparedStatement.setString(3, oldUser.getEmail());
        preparedStatement.executeUpdate();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    public ResponseEntity<Object> deleteUser(User user) throws Exception {
        //validate user
        if(!login(user))  {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        String query = "DELETE FROM user WHERE email = ?";
        PreparedStatement preparedStatement = this.getPreparedStatementFromQuery(query);

        preparedStatement.setString(1, user.getEmail());
        preparedStatement.executeUpdate();
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    public boolean login(User inputUser) throws Exception {
        if(inputUser.hasEmptyFields()){
            return false;
        }

        User databaseUser = this.readUser(inputUser.getEmail());
        return Password.check(inputUser.getPassword(), databaseUser.getPassword());
    }
}
