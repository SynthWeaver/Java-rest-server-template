package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(ResultSet rs) throws SQLException {
        this(
                rs.getObject("email").toString(),
                rs.getObject("password").toString()
        );
    }

    public boolean hasEmptyFields(){
        return email.isEmpty() || password.isEmpty();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
